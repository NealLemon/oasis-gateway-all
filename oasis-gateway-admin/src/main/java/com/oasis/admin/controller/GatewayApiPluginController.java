package com.oasis.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.oasis.common.entity.GatewayApiPlugin;
import com.oasis.common.service.IGatewayApiPluginService;
import com.oasis.common.vo.OasisResponseVO;
import com.oasis.dto.GatewayApiPluginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/25 11:03
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/plugin")
@RequiredArgsConstructor
@Slf4j
public class GatewayApiPluginController {

    private final IGatewayApiPluginService gatewayApiPluginService;

    private final ObjectMapper objectMapper;

    @PostMapping
    public OasisResponseVO<Boolean> addPlugin(@RequestBody GatewayApiPluginDTO gatewayApiPluginDTO) throws JsonProcessingException {
        GatewayApiPlugin gatewayApiPlugin = new GatewayApiPlugin();
        gatewayApiPluginDTO.setPluginConfiguration(objectMapper.writeValueAsString(gatewayApiPluginDTO.getPluginConfigurationJsonNode()));
        BeanUtils.copyProperties(gatewayApiPluginDTO,gatewayApiPlugin);
        return OasisResponseVO.success(gatewayApiPluginService.save(gatewayApiPlugin));
    }

    @PutMapping
    public OasisResponseVO<Boolean> updatePlugin(@RequestBody GatewayApiPluginDTO gatewayApiPluginDTO) {
        GatewayApiPlugin gatewayApiPlugin = new GatewayApiPlugin();
        BeanUtils.copyProperties(gatewayApiPluginDTO,gatewayApiPlugin);
        return OasisResponseVO.success(gatewayApiPluginService.updateById(gatewayApiPlugin));
    }

    @DeleteMapping("/{id}")
    public OasisResponseVO<Boolean> deletePlugin(@PathVariable("id") int id) {
        return OasisResponseVO.success(gatewayApiPluginService.removeById(id));
    }
}
