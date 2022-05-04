package com.oasis.gateway.enums;

public enum GatewayBaseEnum {
    PREDICATE_TYPE(0,"Predicate Type"),
    FILTER_TYPE(1,"Filter Type");


    private Integer code;

    private String desc;

    GatewayBaseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
