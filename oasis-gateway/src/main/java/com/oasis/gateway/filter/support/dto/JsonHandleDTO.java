package com.oasis.gateway.filter.support.dto;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @ClassName JsonHandleDTO
 * @Description JSON处理插件DTO
 * @Author zhushaobin
 * @Date 2022/4/11 15:12
 */
public class JsonHandleDTO {

    //原始请求JSON schema 符合draft-04标准
    private JsonNode requestJsonSchema;

    //扩展JSON 请求
    private JsonExtendDTO extendRequest;

    //扩展JSON 返回
    private JsonExtendDTO extendResponse;

    public JsonHandleDTO(JsonNode requestJsonSchema,
                         JsonExtendDTO extendRequest,
                         JsonExtendDTO extendResponse) {
        this.requestJsonSchema = requestJsonSchema;
        this.extendRequest = extendRequest;
        this.extendResponse = extendResponse;
    }

    public JsonNode getRequestJsonSchema() {
        return requestJsonSchema;
    }

    public void setRequestJsonSchema(JsonNode requestJsonSchema) {
        this.requestJsonSchema = requestJsonSchema;
    }

    public JsonExtendDTO getExtendRequest() {
        return extendRequest;
    }

    public void setExtendRequest(JsonExtendDTO extendRequest) {
        this.extendRequest = extendRequest;
    }

    public JsonExtendDTO getExtendResponse() {
        return extendResponse;
    }

    public void setExtendResponse(JsonExtendDTO extendResponse) {
        this.extendResponse = extendResponse;
    }
}
