package com.oasis.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Neal
 * @since 2022-04-08
 */
@TableName("gateway_api_plugin")
public class GatewayApiPlugin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "plugin_id", type = IdType.AUTO)
    private Integer pluginId;

    /**
     * api_id
     */
    private Integer apiRouterId;

    /**
     * 插件英文属性名
     */
    private String pluginName;

    /**
     * plugin相关配置
     */
    private String pluginConfiguration;

    /**
     * plugin 定义的类别
     */
    private Integer pluginType;

    /**
     * 插件顺序
     */
    private Integer pluginOrder;

    /**
     * 是否启用 0 否 1 是
     */
    private Integer pluginEnabled;

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
     * 是否是原生 1 是 0 否
     */
    private Integer isOrigin;

    /**
     * 是否删除 1 是 0 否
     */
    @TableLogic
    private Integer isDeleted;


    public Integer getPluginId() {
        return pluginId;
    }

    public void setPluginId(Integer pluginId) {
        this.pluginId = pluginId;
    }

    public Integer getApiRouterId() {
        return apiRouterId;
    }

    public void setApiRouterId(Integer apiRouterId) {
        this.apiRouterId = apiRouterId;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginConfiguration() {
        return pluginConfiguration;
    }

    public void setPluginConfiguration(String pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    public Integer getPluginType() {
        return pluginType;
    }

    public void setPluginType(Integer pluginType) {
        this.pluginType = pluginType;
    }

    public Integer getPluginOrder() {
        return pluginOrder;
    }

    public void setPluginOrder(Integer pluginOrder) {
        this.pluginOrder = pluginOrder;
    }

    public Integer getPluginEnabled() {
        return pluginEnabled;
    }

    public void setPluginEnabled(Integer pluginEnabled) {
        this.pluginEnabled = pluginEnabled;
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

    public Integer getIsOrigin() {
        return isOrigin;
    }

    public void setIsOrigin(Integer isOrigin) {
        this.isOrigin = isOrigin;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "GatewayApiPlugin{" +
        "pluginId=" + pluginId +
        ", apiRouterId=" + apiRouterId +
        ", pluginName=" + pluginName +
        ", pluginConfiguration=" + pluginConfiguration +
        ", pluginType=" + pluginType +
        ", pluginOrder=" + pluginOrder +
        ", pluginEnabled=" + pluginEnabled +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        ", isOrigin=" + isOrigin +
        ", isDeleted=" + isDeleted +
        "}";
    }
}
