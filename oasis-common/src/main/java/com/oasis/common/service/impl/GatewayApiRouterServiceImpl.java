package com.oasis.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oasis.common.entity.GatewayApiRouter;
import com.oasis.common.mapper.GatewayApiRouterMapper;
import com.oasis.common.service.IGatewayApiRouterService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * api路由信息表 服务实现类
 * </p>
 *
 * @author Neal
 * @since 2022-04-08
 */
@Service
public class GatewayApiRouterServiceImpl extends ServiceImpl<GatewayApiRouterMapper, GatewayApiRouter> implements IGatewayApiRouterService {

}
