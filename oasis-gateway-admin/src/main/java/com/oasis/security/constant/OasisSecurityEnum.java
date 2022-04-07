package com.oasis.security.constant;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/24 16:36
 * @Description: 安全常量 枚举
 * @Version 1.0.0
 */
public enum OasisSecurityEnum {
    //默认jwt 值前缀
    DEFAULT_AUTHENTICATION_PREFIX("Bearer ");

    private String name;

    OasisSecurityEnum(String name) {
        this.name = name;
    }
}
