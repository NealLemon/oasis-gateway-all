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
@TableName("gateway_group")
public class GatewayGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "api_group_id", type = IdType.AUTO)
    private Integer apiGroupId;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 版本号
     */
    private String groupVersion;

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
     * 是否删除 1删除 0未删除
     */
    @TableLogic
    private Integer isDeleted;


    public Integer getApiGroupId() {
        return apiGroupId;
    }

    public void setApiGroupId(Integer apiGroupId) {
        this.apiGroupId = apiGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupVersion() {
        return groupVersion;
    }

    public void setGroupVersion(String groupVersion) {
        this.groupVersion = groupVersion;
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
        return "GatewayGroup{" +
        "apiGroupId=" + apiGroupId +
        ", groupName=" + groupName +
        ", groupVersion=" + groupVersion +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        ", isDeleted=" + isDeleted +
        "}";
    }
}
