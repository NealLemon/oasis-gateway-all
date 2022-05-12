package com.oasis.gateway.filter.factory;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.gateway.filter.support.dto.JsonHandleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;

import java.lang.reflect.Type;

/**
 * @ClassName OasisJsonHandleFilter
 * @Description JSON处理类
 * @Author zhushaobin
 * @Date 2022/4/11 14:59
 */
@RequiredArgsConstructor
@Slf4j
public class JsonHandleGatewayFilterFactory extends OasisAbstractGatewayFilterFactory {

    private final ObjectMapper objectMapper;


    @Override
    public GatewayFilter apply(Config config) {
        JsonHandleDTO jsonHandleDTO = null;
        try {
            jsonHandleDTO = objectMapper.readValue(config.getConfiguration(), JsonHandleDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonHandleDTO);
        return new OrderedGatewayFilter((exchange, chain) -> chain.filter(exchange), 10);
    }
}
