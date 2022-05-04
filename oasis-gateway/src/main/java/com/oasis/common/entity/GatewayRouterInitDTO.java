package com.oasis.common.entity;

import java.util.List;

/**
 * @ClassName GatewayRouterPluginDTO
 * @Description 网关初始化数据DTO
 * @Author zhushaobin
 * @Date 2022/4/19 14:38
 */
public class GatewayRouterInitDTO {

    private Integer apiId;

    public GatewayRouterInitDTO(Integer apiId) {
        this.apiId = apiId;
    }

    GatewayGroup gatewayGroup;

    GatewayService gatewayService;

    GatewayApiRouter gatewayApiRouter;

    List<GatewayApiPlugin> gatewayApiPlugins;

    public GatewayGroup getGatewayGroup() {
        return gatewayGroup;
    }

    public void setGatewayGroup(GatewayGroup gatewayGroup) {
        this.gatewayGroup = gatewayGroup;
    }

    public GatewayService getGatewayService() {
        return gatewayService;
    }

    public void setGatewayService(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    public GatewayApiRouter getGatewayApiRouter() {
        return gatewayApiRouter;
    }

    public void setGatewayApiRouter(GatewayApiRouter gatewayApiRouter) {
        this.gatewayApiRouter = gatewayApiRouter;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public List<GatewayApiPlugin> getGatewayApiPlugins() {
        return gatewayApiPlugins;
    }

    public void setGatewayApiPlugins(List<GatewayApiPlugin> gatewayApiPlugins) {
        this.gatewayApiPlugins = gatewayApiPlugins;
    }
}
