package com.oasis.admin.controller;

import com.oasis.common.entity.GatewayService;
import com.oasis.common.service.IGatewayServiceService;
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
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class GatewayServiceController {

    private final IGatewayServiceService gatewayServiceService;
    @PostMapping
    public OasisResponseVO<Boolean> addGroup(@RequestBody GatewayService gatewayService) {
        return OasisResponseVO.success(gatewayServiceService.save(gatewayService));
    }

    @PutMapping
    public OasisResponseVO<Boolean> updateGroup(@RequestBody GatewayService gatewayService) {
        return OasisResponseVO.success(gatewayServiceService.updateById(gatewayService));
    }

    @DeleteMapping("/{id}")
    public OasisResponseVO<Boolean> deleteGroup(@PathVariable("id") int id) {
        return OasisResponseVO.success(gatewayServiceService.removeById(id));
    }
}
