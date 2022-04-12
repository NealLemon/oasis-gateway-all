package com.oasis.admin.controller;

import com.oasis.common.entity.GatewayGroup;
import com.oasis.common.service.IGatewayGroupService;
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
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GatewayGroupController {

    private final IGatewayGroupService iGatewayGroupService;
    @PostMapping
    public OasisResponseVO<Boolean> addGroup(@RequestBody GatewayGroup gatewayGroup) {
        return OasisResponseVO.success(iGatewayGroupService.save(gatewayGroup));
    }

    @PutMapping
    public OasisResponseVO<Boolean> updateGroup(@RequestBody GatewayGroup gatewayGroup) {
        return OasisResponseVO.success(iGatewayGroupService.updateById(gatewayGroup));
    }

    @DeleteMapping("/{id}")
    public OasisResponseVO<Boolean> deleteGroup(@PathVariable("id") int id) {
        return OasisResponseVO.success(iGatewayGroupService.removeById(id));
    }
}
