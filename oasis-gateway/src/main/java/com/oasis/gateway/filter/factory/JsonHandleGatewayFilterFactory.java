package com.oasis.gateway.filter.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.load.Dereferencing;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.oasis.gateway.filter.support.dto.JsonHandleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @ClassName OasisJsonHandleFilter
 * @Description JSON处理类
 * @Author zhushaobin
 * @Date 2022/4/11 14:59
 */
@Slf4j
public class JsonHandleGatewayFilterFactory extends OasisAbstractGatewayFilterFactory {

    private final ObjectMapper objectMapper;

    private final List<HttpMessageReader<?>> messageReaders;

    private final JsonSchemaFactory jsonSchemaInlineFactory;

    public JsonHandleGatewayFilterFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
        LoadingConfiguration cfg = LoadingConfiguration.newBuilder()
                .dereferencing(Dereferencing.INLINE).freeze();
        jsonSchemaInlineFactory = JsonSchemaFactory.newBuilder()
                .setLoadingConfiguration(cfg).freeze();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new JsonHandlerDefaultPlugin(config);
    }

    public class JsonHandlerDefaultPlugin implements  GatewayFilter, Ordered {

        private final Config config;

        public JsonHandlerDefaultPlugin(Config config) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            try {
                JsonHandleDTO jsonHandleDTO = objectMapper.readValue(config.getConfiguration(), JsonHandleDTO.class);
                JsonNode jsonSchema = jsonHandleDTO.getRequestJsonSchema();
                JsonSchema validateSchema = jsonSchemaInlineFactory.getJsonSchema(jsonSchema);
                ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
                return serverRequest.bodyToMono(JsonNode.class).flatMap(jsonNode -> {
                    ProcessingReport report =  validateSchema.validateUnchecked(jsonNode);
                        if(report.isSuccess()) {
                            return chain.filter(exchange);
                        }
                      return Mono.error(new Exception(report.iterator().next().getMessage()));
                });
            } catch (Exception e) {
                return Mono.error(e);
            }
        }

        @Override
        public int getOrder() {
            return 10;
        }
    }


//    @Override
//    public GatewayFilter apply(Config config) {
//        try {
//            JsonHandleDTO jsonHandleDTO = objectMapper.readValue(config.getConfiguration(),JsonHandleDTO.class);
//            JsonNode jsonSchema
//                    = jsonHandleDTO.getRequestJsonSchema();
//            JsonSchema validateSchema = jsonSchemaInlineFactory.getJsonSchema(jsonSchema);
//            return new OrderedGatewayFilter(((exchange, chain) -> {
//                ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
//                return serverRequest.bodyToMono(JsonNode.class).flatMap(jsonNode -> {
//                    ProcessingReport report =  validateSchema.validateUnchecked(jsonNode);
//                        if(report.isSuccess()) {
//                            return chain.filter(exchange);
//                        }
//                      return Mono.error(new Exception("error"));
//                });
//            }),10);
//        } catch (Exception e) {
//            log.error("error",e);
//            return new OrderedGatewayFilter((exchange, chain) -> return Mono.error(new Exception(e));,10);
//        }
//    }
}
