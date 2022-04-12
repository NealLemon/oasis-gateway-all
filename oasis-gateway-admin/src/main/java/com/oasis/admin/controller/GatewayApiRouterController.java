package com.oasis.admin.controller;

import com.oasis.common.entity.GatewayApiRouter;
import com.oasis.common.service.IGatewayApiRouterService;
import com.oasis.common.vo.OasisResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/25 11:03
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/router")
@RequiredArgsConstructor
public class GatewayApiRouterController {

    private final IGatewayApiRouterService gatewayApiPlugin;

    @PostMapping
    public OasisResponseVO<Boolean> addRouter(@RequestBody GatewayApiRouter gatewayApiRouter) {
        return OasisResponseVO.success(gatewayApiPlugin.save(gatewayApiRouter));
    }

    @PutMapping
    public OasisResponseVO<Boolean> updateRouter(@RequestBody GatewayApiRouter gatewayApiRouter) {
        return OasisResponseVO.success(gatewayApiPlugin.updateById(gatewayApiRouter));
    }

    @DeleteMapping("/{id}")
    public OasisResponseVO<Boolean> deleteRouter(@PathVariable("id") int id) {
        return OasisResponseVO.success(gatewayApiPlugin.removeById(id));
    }
}
