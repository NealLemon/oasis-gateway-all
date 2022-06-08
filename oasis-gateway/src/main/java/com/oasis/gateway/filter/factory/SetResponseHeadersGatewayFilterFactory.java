package com.oasis.gateway.filter.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.oasis.common.entity.GatewayApiPlugin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RewriteResponseHeaderGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SetResponseHeadersGatewayFilterFactory
 * @Description 设置多个返回请求头
 * @Author zhushaobin
 * @Date 2022/4/19 11:29
 */
@RequiredArgsConstructor
@Slf4j
public class SetResponseHeadersGatewayFilterFactory extends OasisAbstractGatewayFilterFactory{

    private final ObjectMapper objectMapper;

    @Override
    public GatewayFilter apply(Config config) {
       //     Map<String,Object> headersMap = objectMapper.readValue(config.getConfiguration(), Map.class);
            return new OrderedGatewayFilter((exchange, chain) -> chain.filter(exchange)
                    .then(Mono.fromRunnable(()-> rewriteHeaders(exchange,config))),11);
    }

    protected void rewriteHeaders(ServerWebExchange exchange, Config config) {
        try {
            GatewayApiPlugin gatewayApiPlugin = objectMapper.readValue(config.getConfiguration(), GatewayApiPlugin.class);
            Map<String,String> headersMap = objectMapper.readValue(gatewayApiPlugin.getPluginConfiguration(), Map.class);
            HttpHeaders responseHeaders = exchange.getResponse().getHeaders();
           for(Map.Entry<String,String> entry : headersMap.entrySet()) {
            responseHeaders.put(entry.getKey(), ImmutableList.of(entry.getValue()));
        }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
