package com.oasis.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.common.mapper.GatewayRouterInitDTOMapper;
import com.oasis.gateway.route.DynamicDataBaseDefinitionRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName OasisGatewayAutoConfiguration
 * @Description Oasis网关自动装配类
 * @Author zhushaobin
 * @Date 2022/4/19 15:17
 */
@ConditionalOnClass(GatewayAutoConfiguration.class)
@AutoConfigureAfter({ GatewayAutoConfiguration.class})
@Configuration
public class OasisGatewayAutoConfiguration {

    @Bean
    public DynamicDataBaseDefinitionRepository dynamicDataBaseDefinitionRepository(GatewayRouterInitDTOMapper gatewayRouterInitDTOMapper,
                                                                                   ObjectMapper objectMapper) {
        return new DynamicDataBaseDefinitionRepository(gatewayRouterInitDTOMapper,objectMapper);

    }
}
