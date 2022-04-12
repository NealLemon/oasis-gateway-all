package com.oasis.gateway.filter.factory;

import com.oasis.gateway.enums.OasisPluginOrder;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;

/**
 * @ClassName OasisJsonHandleFilter
 * @Description JSON处理类
 * @Author zhushaobin
 * @Date 2022/4/11 14:59
 */
public class JsonHandleGatewayFilterFactory extends OasisAbstractGatewayFilterFactory {



    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            return null;
        }, OasisPluginOrder.JSON_HANDLE_ORDER.getOrder());
    }
}
