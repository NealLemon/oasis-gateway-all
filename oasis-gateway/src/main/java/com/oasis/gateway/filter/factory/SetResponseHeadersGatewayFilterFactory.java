package com.oasis.gateway.filter.factory;

import com.oasis.gateway.filter.factory.enums.OasisPluginOrder;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SetResponseHeadersGatewayFilterFactory
 * @Description 设置多个返回请求头
 * @Author zhushaobin
 * @Date 2022/4/19 11:29
 */
public class SetResponseHeadersGatewayFilterFactory extends OasisAbstractGatewayFilterFactory{


    @Override
    public GatewayFilter apply(Config config) {
        Map<String,String> headersMap = new HashMap<>();
        return new OrderedGatewayFilter((exchange, chain) -> {
                return chain.filter(exchange)
                        .then(Mono.fromRunnable(() -> addHeaders(exchange.getResponse().getHeaders(),headersMap)));
        }, OasisPluginOrder.JSON_HANDLE_ORDER.getOrder());
    }

    private void addHeaders(HttpHeaders headers, Map<String,String> headersMap) {
        for(Map.Entry<String, String> entry : headersMap.entrySet()) {
            headers.set(entry.getKey(),entry.getValue());
        }
    }
}
