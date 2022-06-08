package com.oasis.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

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
@Data
public class GatewayApiPluginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
    @JsonAlias("pluginConfiguration")
    private JsonNode pluginConfigurationJsonNode;

    /**
     * plugin相关配置
     */
    @JsonIgnore
    private String pluginConfiguration;

    /**
     * plugin 定义的类别 0 Predicate  1 filter
     */
    private Integer pluginType;

    /**
     * 插件顺序
     */
    private Integer pluginOrder;

    /**
     * 是否启用 0 否 1 是
     */
    private boolean pluginEnabled;

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
    private boolean isOrigin;

    /**
     * 是否删除 1 是 0 否
     */
    private Integer isDeleted;


}
