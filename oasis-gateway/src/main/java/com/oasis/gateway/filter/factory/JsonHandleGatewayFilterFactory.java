package com.oasis.gateway.filter.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.load.Dereferencing;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.oasis.common.entity.GatewayApiPlugin;
import com.oasis.gateway.filter.support.dto.JsonHandleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
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
            return new JsonHandlerDefaultPlugin(config);
        } catch (JsonProcessingException e) {
            log.error("JsonHandleGatewayFilterFactory initialization has error : ",e);
            return new DefaultErrorInitializationFilter();
        }
    }

    public class JsonHandlerDefaultPlugin implements  GatewayFilter, Ordered {

        private final GatewayApiPlugin gatewayApiPlugin;

        public JsonHandlerDefaultPlugin(Config config) throws JsonProcessingException {
            gatewayApiPlugin = objectMapper.readValue(config.getConfiguration(),GatewayApiPlugin.class);
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            try {
                JsonHandleDTO jsonHandleDTO = objectMapper.readValue(gatewayApiPlugin.getPluginConfiguration(), JsonHandleDTO.class);
                JsonNode jsonSchema = jsonHandleDTO.getRequestJsonSchema();
                JsonSchema validateSchema = jsonSchemaInlineFactory.getJsonSchema(jsonSchema);
                ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
                Mono<JsonNode> modifiedBody = serverRequest.bodyToMono(JsonNode.class)
                        .flatMap(originalBody -> modifyRequestBody()
                                .apply(exchange, Mono.just(originalBody)));
                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, JsonNode.class);
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());

                // the new content type will be computed by bodyInserter
                // and then set in the request decorator
                headers.remove(HttpHeaders.CONTENT_LENGTH);
//                return serverRequest.bodyToMono(JsonNode.class).flatMap(jsonNode -> {
//                    ProcessingReport report =  validateSchema.validateUnchecked(jsonNode);
//                        if(report.isSuccess()) {
//                            return chain.filter(exchange);
//                        }
//                      return Mono.error(new Exception(report.iterator().next().getMessage()));
//                });
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        // .log("modify_request", Level.INFO)
                        .then(Mono.defer(() -> {
                            ServerHttpRequest decorator = decorate(exchange, headers, outputMessage);
                            return chain.filter(exchange.mutate().request(decorator).build());
                        })).onErrorResume((Function<Throwable, Mono<Void>>) throwable -> release(exchange,
                                outputMessage, throwable));
            } catch (Exception e) {
                return Mono.error(e);
            }
        }

        @Override
        public int getOrder() {
            return gatewayApiPlugin.getPluginOrder();
        }

        /**
         * 修改请求体
         *
         * @return  请求结果
         */
        protected BiFunction<ServerWebExchange, Mono<JsonNode>, Mono<JsonNode>> modifyRequestBody() {
            return (exchange, json) -> {
                AtomicReference<JsonNode> result = new AtomicReference<>();
                json.subscribe(
                        body -> {
                            JsonNode newBody = null;
                            try {
                                newBody = modifyRequestBody(exchange, body);
                            } catch (Exception e) {
                                log.error("modifyRequestBody has error: {}",e);
                            }
                            result.set(newBody);
                        }
                );
                return Mono.just(result.get());
            };
        }

        /**
         * 子类复写,修改请求体
         * @param exchange  上下文
         * @param body      请求体
         * @return          修改后的请求体
         */
        public JsonNode modifyRequestBody(ServerWebExchange exchange, JsonNode body) {
            return body;
        }

         Mono<Void> release(ServerWebExchange exchange, CachedBodyOutputMessage outputMessage,
                                     Throwable throwable) {
            return Mono.error(throwable);
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
