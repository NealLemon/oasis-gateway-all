package com.oasis.gateway.filter.factory.enums;


/**
 * @ClassName OasisPluginOrder
 * @Description 插件顺序
 * @Author zhushaobin
 * @Date 2022/4/11 15:04
 */
public enum OasisPluginOrder {

    DeFAULT_ORDER(0),
    JSON_HANDLE_ORDER(-10);

    private int order;

    OasisPluginOrder(int order) {
        this.order = order;
    }
    public int getOrder() {
        return order;
    }

}
