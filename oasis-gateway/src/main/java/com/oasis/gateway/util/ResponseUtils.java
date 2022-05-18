package com.oasis.gateway.util;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @ClassName ResponseUtils
 * @Description 封装结果返回
 * @Author zhushaobin
 * @Date 2022/5/18 22:45
 */
public class ResponseUtils {
    private static DefaultDataBufferFactory defaultDataBufferFactory = new DefaultDataBufferFactory();

    public static Mono<Void> responseBody (ServerWebExchange exchange, MediaType mediaType, String responseBody) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        try{
            response.getHeaders().setContentType(mediaType);
        }catch (Exception e) {
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }
        if(null != responseBody) {
            DataBuffer dataBuffer = defaultDataBufferFactory.wrap(responseBody.getBytes());
            Flux<DataBuffer> body = Flux.just(dataBuffer);
            return response.writeAndFlushWith(body.map(Flux::just));
        }else{
            return response.writeWith(Mono.empty());
        }
    }



}
