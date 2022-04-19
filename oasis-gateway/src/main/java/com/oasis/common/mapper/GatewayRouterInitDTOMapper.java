package com.oasis.common.mapper;

import com.oasis.common.entity.GatewayRouterInitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName GatewayRouterInitDTOMapper
 * @Description 初始化gateway自定义mapper
 * @Author zhushaobin
 * @Date 2022/4/19 14:51
 */
public interface GatewayRouterInitDTOMapper {
    /**
     * @name: getAllRoutersAndPlugin
     * @description: 获取所有路由、插件、以及服务和分组信息
     * @return: java.util.List<com.oasis.gateway.route.entity.GatewayRouterInitDTO>
     * @date: 2022/4/19 14:53
     * @auther: zhushaobin
     *
    */
    List<GatewayRouterInitDTO> getAllRoutersAndPlugin(@Param("apiId") Integer apiId);
}
