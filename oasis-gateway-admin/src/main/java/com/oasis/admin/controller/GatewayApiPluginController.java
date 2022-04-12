package com.oasis.admin.controller;

import com.oasis.common.entity.GatewayApiPlugin;
import com.oasis.common.service.IGatewayApiPluginService;
import com.oasis.common.vo.OasisResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping
    public OasisResponseVO<Boolean> addPlugin(@RequestBody GatewayApiPlugin gatewayApiPlugin) {
            return OasisResponseVO.success(gatewayApiPluginService.save(gatewayApiPlugin));
    }

    @PutMapping
    public OasisResponseVO<Boolean> updatePlugin(@RequestBody GatewayApiPlugin gatewayApiPlugin) {
        return OasisResponseVO.success(gatewayApiPluginService.updateById(gatewayApiPlugin));
    }

    @DeleteMapping("/{id}")
    public OasisResponseVO<Boolean> deletePlugin(@PathVariable("id") int id) {
        return OasisResponseVO.success(gatewayApiPluginService.removeById(id));
    }
}
