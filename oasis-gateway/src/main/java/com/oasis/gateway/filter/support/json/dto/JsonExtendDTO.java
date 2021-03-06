package com.oasis.gateway.filter.support.json.dto;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName JsonExtendDTO
 * @Description 扩展JSON数据处理 不包括数据校验
 * @Author zhushaobin
 * @Date 2022/4/11 15:38
 */
public class JsonExtendDTO implements Serializable {

    //新增Json字段
    private Map<String,ArrayNode> addJsonNodes;
    //删除JSON字段
    private ArrayNode deleteJsonNodes;

    public JsonExtendDTO() {
    }

    public JsonExtendDTO(Map<String, ArrayNode> addJsonNodes, ArrayNode deleteJsonNodes) {
        this.addJsonNodes = addJsonNodes;
        this.deleteJsonNodes = deleteJsonNodes;
    }

    public Map<String, ArrayNode> getAddJsonNodes() {
        return addJsonNodes;
    }

    public ArrayNode getDeleteJsonNodes() {
        return deleteJsonNodes;
    }
}
