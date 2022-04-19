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

    List<GatewayApiPlugin> gatewayApiPluginList;

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

    public List<GatewayApiPlugin> getGatewayApiPluginList() {
        return gatewayApiPluginList;
    }

    public void setGatewayApiPluginList(List<GatewayApiPlugin> gatewayApiPluginList) {
        this.gatewayApiPluginList = gatewayApiPluginList;
    }


}
