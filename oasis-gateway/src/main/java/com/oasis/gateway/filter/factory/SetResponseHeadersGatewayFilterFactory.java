package com.oasis.gateway.filter.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

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
        try {
            Map<String,Object> headersMap = objectMapper.readValue(config.getConfiguration(), Map.class);
            return new OrderedGatewayFilter((exchange, chain) -> chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> addHeaders(exchange.getResponse().getHeaders(),headersMap))), -10);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addHeaders(HttpHeaders headers, Map<String,Object> mapHeaders) {
        for(Map.Entry<String, Object> entry : mapHeaders.entrySet()) {
            headers.set(entry.getKey(),String.valueOf(entry.getValue()));
        }
    }
}
