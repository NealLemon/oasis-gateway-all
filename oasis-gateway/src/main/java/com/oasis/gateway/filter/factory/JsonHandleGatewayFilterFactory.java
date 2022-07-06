package com.oasis.gateway.filter.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.load.Dereferencing;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.oasis.common.entity.GatewayApiPlugin;
import com.oasis.gateway.filter.support.json.DefaultRequestBodyRewrite;
import com.oasis.gateway.filter.support.json.dto.JsonHandleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

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
        try {
            GatewayApiPlugin gatewayApiPlugin = objectMapper.readValue(config.getConfiguration(),GatewayApiPlugin.class);
            JsonHandleDTO jsonHandleDTO = objectMapper.readValue(gatewayApiPlugin.getPluginConfiguration(), JsonHandleDTO.class);
            JsonNode jsonSchema = jsonHandleDTO.getRequestJsonSchema();
            JsonSchema validateSchema = jsonSchemaInlineFactory.getJsonSchema(jsonSchema);
            DefaultRequestBodyRewrite defaultRequestBodyRewrite = new DefaultRequestBodyRewrite(validateSchema);
            return new JsonHandlerDefaultPlugin(defaultRequestBodyRewrite,gatewayApiPlugin.getPluginOrder());
        } catch (JsonProcessingException | ProcessingException e) {
            log.error("JsonHandleGatewayFilterFactory initialization has error : ",e);
            return new DefaultErrorInitializationFilter();
        }
    }

    public class JsonHandlerDefaultPlugin implements  GatewayFilter, Ordered {

        private final DefaultRequestBodyRewrite defaultRequestBodyRewrite;
        private int order;
        public JsonHandlerDefaultPlugin(DefaultRequestBodyRewrite defaultRequestBodyRewrite, int order) {
            this.defaultRequestBodyRewrite = defaultRequestBodyRewrite;
            this.order = order;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            try {
                ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
                Mono<JsonNode> modifiedBody = serverRequest.bodyToMono(JsonNode.class)
                        .flatMap(originalBody -> (Mono<JsonNode>) defaultRequestBodyRewrite.apply(exchange, originalBody))
                        .switchIfEmpty(Mono.defer(() -> (Mono<JsonNode>) defaultRequestBodyRewrite.apply(exchange, null)));
                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, JsonNode.class);
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());
                // the new content type will be computed by bodyInserter
                // and then set in the request decorator
                headers.remove(HttpHeaders.CONTENT_LENGTH);
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        // .log("modify_request", Level.INFO)
                        .then(Mono.defer(() -> {
                            ServerHttpRequest decorator = decorate(exchange, headers, outputMessage);
                            return chain.filter(exchange.mutate().request(decorator).build());
                        })).onErrorResume((Function<Throwable, Mono<Void>>) throwable -> Mono.error(throwable));
            } catch (Exception e) {
                return Mono.error(e);
            }
        }

        @Override
        public int getOrder() {
            return order;
        }

        ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                            CachedBodyOutputMessage outputMessage) {
            return new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public HttpHeaders getHeaders() {
                    long contentLength = headers.getContentLength();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.putAll(headers);
                    if (contentLength > 0) {
                        httpHeaders.setContentLength(contentLength);
                    }
                    else {
                        // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
                        // httpbin.org
                        httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                    }
                    return httpHeaders;
                }

                @Override
                public Flux<DataBuffer> getBody() {
                    return outputMessage.getBody();
                }
            };
        }
    }

}
