package com.oasis.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * api路由信息表
 * </p>
 *
 * @author Neal
 * @since 2022-04-19
 */
@TableName("gateway_api_router")
public class GatewayApiRouter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "api_router_id", type = IdType.AUTO)
    private Integer apiRouterId;

    /**
     * 路由版本
     */
    private String routerVersion;

    /**
     * 路由名称
     */
    private String routerName;

    /**
     * 路由Path
     */
    private String routerPath;

    /**
     * 请求方法 GET/POST/PUT/DELETE
     */
    private String requestMethod;

    /**
     * 路由类型 1.api 2.service 3.注册中心
     */
    private Integer routerType;

    /**
     * 上游服务 Path
     */
    private String routerUpstreamPath;

    /**
     * 上游服务ip+port
     */
    private String routerUpstreamUrl;

    /**
     * 上游服务,仅当router_type为2或3时可用,2存gateway_service 表中的ID,3存注册中心对应的服务名
     */
    private Integer routerUpstreamService;

    /**
     * api协议 1.http 2.rpc(dubbo)
     */
    private Integer protocol;

    /**
     * 消费类别
     */
    private String consumesType;

    /**
     * 生产类别
     */
    private String producesType;

    /**
     * 服务ID
     */
    private Integer serviceId;

    /**
     * 组ID
     */
    private Integer apiGroupId;

    /**
     * 路由描述
     */
    private String routerDesc;

    /**
     * 路由请求体示例
     */
    private String routerRequestBody;

    /**
     * 路由响应体示例
     */
    private String routerResponseBody;

    /**
     * 用户ID 用于控制API 创建权限
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户ID 用于控制API 修改权限
     */
    private String updateUser;

    /**
     * 是否删除 1、删除 0、未删除
     */
    private Integer isDeleted;


    public Integer getApiRouterId() {
        return apiRouterId;
    }

    public void setApiRouterId(Integer apiRouterId) {
        this.apiRouterId = apiRouterId;
    }

    public String getRouterVersion() {
        return routerVersion;
    }

    public void setRouterVersion(String routerVersion) {
        this.routerVersion = routerVersion;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Integer getRouterType() {
        return routerType;
    }

    public void setRouterType(Integer routerType) {
        this.routerType = routerType;
    }

    public String getRouterUpstreamPath() {
        return routerUpstreamPath;
    }

    public void setRouterUpstreamPath(String routerUpstreamPath) {
        this.routerUpstreamPath = routerUpstreamPath;
    }

    public String getRouterUpstreamUrl() {
        return routerUpstreamUrl;
    }

    public void setRouterUpstreamUrl(String routerUpstreamUrl) {
        this.routerUpstreamUrl = routerUpstreamUrl;
    }

    public Integer getRouterUpstreamService() {
        return routerUpstreamService;
    }

    public void setRouterUpstreamService(Integer routerUpstreamService) {
        this.routerUpstreamService = routerUpstreamService;
    }

    public Integer getProtocol() {
        return protocol;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public String getConsumesType() {
        return consumesType;
    }

    public void setConsumesType(String consumesType) {
        this.consumesType = consumesType;
    }

    public String getProducesType() {
        return producesType;
    }

    public void setProducesType(String producesType) {
        this.producesType = producesType;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getApiGroupId() {
        return apiGroupId;
    }

    public void setApiGroupId(Integer apiGroupId) {
        this.apiGroupId = apiGroupId;
    }

    public String getRouterDesc() {
        return routerDesc;
    }

    public void setRouterDesc(String routerDesc) {
        this.routerDesc = routerDesc;
    }

    public String getRouterRequestBody() {
        return routerRequestBody;
    }

    public void setRouterRequestBody(String routerRequestBody) {
        this.routerRequestBody = routerRequestBody;
    }

    public String getRouterResponseBody() {
        return routerResponseBody;
    }

    public void setRouterResponseBody(String routerResponseBody) {
        this.routerResponseBody = routerResponseBody;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "GatewayApiRouter{" +
        "apiRouterId=" + apiRouterId +
        ", routerVersion=" + routerVersion +
        ", routerName=" + routerName +
        ", routerPath=" + routerPath +
        ", requestMethod=" + requestMethod +
        ", routerType=" + routerType +
        ", routerUpstreamPath=" + routerUpstreamPath +
        ", routerUpstreamUrl=" + routerUpstreamUrl +
        ", routerUpstreamService=" + routerUpstreamService +
        ", protocol=" + protocol +
        ", consumesType=" + consumesType +
        ", producesType=" + producesType +
        ", serviceId=" + serviceId +
        ", apiGroupId=" + apiGroupId +
        ", routerDesc=" + routerDesc +
        ", routerRequestBody=" + routerRequestBody +
        ", routerResponseBody=" + routerResponseBody +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        ", isDeleted=" + isDeleted +
        "}";
    }
}
