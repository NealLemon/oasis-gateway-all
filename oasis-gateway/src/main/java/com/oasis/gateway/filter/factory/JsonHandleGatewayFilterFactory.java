package com.oasis.gateway.filter.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.gateway.filter.factory.enums.OasisPluginOrder;
import com.oasis.gateway.filter.support.dto.JsonHandleDTO;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;

/**
 * @ClassName OasisJsonHandleFilter
 * @Description JSON处理类
 * @Author zhushaobin
 * @Date 2022/4/11 14:59
 */
public class JsonHandleGatewayFilterFactory extends OasisAbstractGatewayFilterFactory {

    private ObjectMapper objectMapper;


    @Override
    public GatewayFilter apply(Config config) {

        return new OrderedGatewayFilter((exchange, chain) -> {
            return null;
        }, OasisPluginOrder.JSON_HANDLE_ORDER.getOrder());
    }
}
