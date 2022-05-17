package com.oasis.gateway.filter.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.oasis.gateway.filter.support.dto.JsonHandleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStreamReader;
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

    public JsonHandleGatewayFilterFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    private static final JsonValidator VALIDATOR
            = JsonSchemaFactory.byDefault().getValidator();

    @Override
    public GatewayFilter apply(Config config) {
        try {
            JsonHandleDTO jsonHandleDTO = objectMapper.readValue(config.getConfiguration(),JsonHandleDTO.class);
            JsonNode jsonSchema
                    = jsonHandleDTO.getRequestJsonSchema();
//            return new OrderedGatewayFilter((exchange, chain) -> {
//              Mono<Boolean>  result =Mono.from(exchange.getRequest().getBody().flatMap(dataBuffer -> {
//                    InputStreamReader inputStream = new InputStreamReader(dataBuffer.asInputStream());
//                    try {
//                        JsonNode json
//                                = JsonLoader.fromReader(inputStream);
//                        ProcessingReport report = VALIDATOR.validate(jsonSchema, json);
//                        if (!report.isSuccess()) {
//                            return Mono.just(false);
//                        }
//                    } catch (Exception e) {
//                        return Mono.just(false);
//                    }
//                    return Mono.just(true);
//                }));
//              return chain.filter(exchange);
//            }, Integer.MIN_VALUE);
            return new OrderedGatewayFilter(((exchange, chain) -> {
                ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
                serverRequest.bodyToMono(String.class).flatMap(s -> {
                    System.out.println(s);
                    return Mono.empty();
                }).subscribe().dispose();
                return chain.filter(exchange);
            }),10);
        } catch (JsonProcessingException e) {
            log.error("error",e);
            return new OrderedGatewayFilter((exchange, chain) -> chain.filter(exchange),10);
        }
    }
}
