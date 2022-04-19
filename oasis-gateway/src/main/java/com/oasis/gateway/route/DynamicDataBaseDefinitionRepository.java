package com.oasis.gateway.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.common.entity.GatewayRouterInitDTO;
import com.oasis.common.mapper.GatewayRouterInitDTOMapper;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

/**
 * @ClassName OasisRouteLocator
 * @Description 网关初始化仓储（数据源为 数据库）
 * @Author zhushaobin
 * @Date 2022/4/19 10:46
 */
public class DynamicDataBaseDefinitionRepository implements RouteDefinitionRepository {

    private final GatewayRouterInitDTOMapper gatewayRouterInitDTOMapper;

    private final ObjectMapper objectMapper;

    public DynamicDataBaseDefinitionRepository(GatewayRouterInitDTOMapper gatewayRouterInitDTOMapper,
                                               ObjectMapper objectMapper) {
        this.gatewayRouterInitDTOMapper = gatewayRouterInitDTOMapper;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initOasisGateway() {
        List<GatewayRouterInitDTO> list = gatewayRouterInitDTOMapper.getAllRoutersAndPlugin(null);
        System.out.println(list);
    }
    private final Map<String, RouteDefinition> routes = synchronizedMap(
            new LinkedHashMap<String, RouteDefinition>());

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        Map<String, RouteDefinition> routesSafeCopy = new LinkedHashMap<>(routes);
        return Flux.fromIterable(routesSafeCopy.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            if (ObjectUtils.isEmpty(r.getId())) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (routes.containsKey(id)) {
                routes.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
        });
    }
}
