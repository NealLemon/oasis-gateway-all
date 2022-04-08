package com.oasis.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * api分组表
 * </p>
 *
 * @author Neal
 * @since 2022-04-08
 */
@TableName("gateway_service")
public class GatewayService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "service_id", type = IdType.AUTO)
    private Integer serviceId;

    /**
     * 组名称
     */
    private String serviceName;

    /**
     * 版本号
     */
    private String serviceVersion;

    /**
     * 服务节点,可以多个
     */
    private String serviceNodes;

    /**
     * 用户 创建权限
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
     * 用户 修改权限
     */
    private String updateUser;

    /**
     * 是否删除 1删除 0未删除
     */
    @TableLogic
    private Integer isDeleted;


    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getServiceNodes() {
        return serviceNodes;
    }

    public void setServiceNodes(String serviceNodes) {
        this.serviceNodes = serviceNodes;
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
        return "GatewayService{" +
        "serviceId=" + serviceId +
        ", serviceName=" + serviceName +
        ", serviceVersion=" + serviceVersion +
        ", serviceNodes=" + serviceNodes +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        ", isDeleted=" + isDeleted +
        "}";
    }
}
