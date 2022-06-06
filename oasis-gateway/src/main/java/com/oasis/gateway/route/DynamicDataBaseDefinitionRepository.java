package com.oasis.gateway.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasis.common.entity.GatewayApiPlugin;
import com.oasis.common.entity.GatewayApiRouter;
import com.oasis.common.entity.GatewayRouterInitDTO;
import com.oasis.common.mapper.GatewayRouterInitDTOMapper;
import com.oasis.gateway.enums.GatewayBaseConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.synchronizedMap;

/**
 * @ClassName OasisRouteLocator
 * @Description 网关初始化仓储（数据源为 数据库）
 * @Author zhushaobin
 * @Date 2022/4/19 10:46
 */
@Slf4j
public class DynamicDataBaseDefinitionRepository implements RouteDefinitionRepository {

    private static final String PATH_ROUTER = "Path";
    private static final String METHOD_ROUTER = "Method";
    private final GatewayRouterInitDTOMapper gatewayRouterInitDTOMapper;

    private final ObjectMapper objectMapper;

    public DynamicDataBaseDefinitionRepository(GatewayRouterInitDTOMapper gatewayRouterInitDTOMapper,
                                               ObjectMapper objectMapper) {
        this.gatewayRouterInitDTOMapper = gatewayRouterInitDTOMapper;
        this.objectMapper = objectMapper;
    }


    private final Map<String, RouteDefinition> routes = synchronizedMap(
            new LinkedHashMap<>());


    @PostConstruct
    public void initOasisGateway() {
        List<GatewayRouterInitDTO> list = gatewayRouterInitDTOMapper.getAllRoutersAndPlugin(null);
        constructRouteDefinition(list);
    }

    /**
     * @name: constructRouteDefinition
     * @description: 装配网关路由以及插件
     * @param list
     * @return: void
     * @date: 2022/5/4 10:14
     * @auther: zhushaobin
     *
    */
    private void constructRouteDefinition(List<GatewayRouterInitDTO> list) {
        log.info("[OASIS GATEWAY] start init Predicate");
        list.stream().filter(gatewayRouterInitDTO -> gatewayRouterInitDTO.getGatewayApiRouter().getIsEnabled()).forEach(gatewayRouterInitDTO -> {
            RouteDefinition route = new RouteDefinition();
            List<PredicateDefinition> predicates = initRoutePredicates(gatewayRouterInitDTO);
            List<FilterDefinition> filterDefinitions = initFilterDefinitions(gatewayRouterInitDTO.getGatewayApiPlugins().stream()
                    .filter(gatewayApiPlugin ->
                            gatewayApiPlugin.isPluginEnabled()
                                    && GatewayBaseConstants.FILTER_TYPE == gatewayApiPlugin.getPluginType())
                    .collect(Collectors.toList()));
            route.setFilters(filterDefinitions);
            route.setPredicates(predicates);
            route.setId(GatewayBaseConstants.ROUTE_PREFIX + gatewayRouterInitDTO.getApiId());
            try {
                route.setUri(new URI(gatewayRouterInitDTO.getGatewayApiRouter().getRouterUpstreamUrl()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            //TODO
            //route.setMetadata();
            save(Mono.just(route)).subscribe();
        });
        log.info("[OASIS GATEWAY] end init Predicate");
    }

    /**
     * @name: initFilterDefinitions
     * @description: 初始化插件filter
     * @param gatewayApiPlugins
     * @return: java.util.List<org.springframework.cloud.gateway.filter.FilterDefinition>
     * @date: 2022/5/4 12:40
     * @auther: zhushaobin
     *
    */
    private List<FilterDefinition> initFilterDefinitions(List<GatewayApiPlugin> gatewayApiPlugins) {
        List<FilterDefinition> filterDefinitions = new ArrayList<>();
        try {
        /**spring cloud 原生filter**/
            for (GatewayApiPlugin gatewayApiPlugin : gatewayApiPlugins) {
                if(gatewayApiPlugin.isOrigin()) {
                    filterDefinitions.add(new FilterDefinition(gatewayApiPlugin.getPluginConfiguration()));
                }else{           /**自定义 filter**/
                    FilterDefinition filterDefinition = new FilterDefinition();
                    filterDefinition.setName(gatewayApiPlugin.getPluginName());
                    filterDefinition.setArgs(Map.of(GatewayBaseConstants.CONFIG_KEY,objectMapper.writeValueAsString(gatewayApiPlugin)));
                    filterDefinitions.add(filterDefinition);
                }
            }
        } catch (JsonProcessingException e) {
            log.error("Init Filter is error: {}",e);
        }
        return filterDefinitions;
    }


    /**
     * @name: initRoutePredicates
     * @description: 初始化路由前置断言
     * @param gatewayRouterInitDTO
     * @return: java.util.List<org.springframework.cloud.gateway.handler.predicate.PredicateDefinition>
     * @date: 2022/5/4 10:40
     * @auther: zhushaobin
     *
    */
    private List<PredicateDefinition> initRoutePredicates(GatewayRouterInitDTO gatewayRouterInitDTO) {
        List<PredicateDefinition> predicates = new ArrayList<>();
        GatewayApiRouter gatewayApiRouter = gatewayRouterInitDTO.getGatewayApiRouter();
        /**
         * 装配基础路由信息
         */
        //装配Path路由
        predicates.add(assemblePredicateDefinition(PATH_ROUTER,gatewayApiRouter.getRouterPath()));
        //装配method路由
        predicates.add(assemblePredicateDefinition(METHOD_ROUTER,gatewayApiRouter.getRequestMethod()));
        /**
         * 装备插件类 路由信息
         */
        gatewayRouterInitDTO.getGatewayApiPlugins().stream()
                .filter(gatewayApiPlugin -> gatewayApiPlugin.isPluginEnabled() && GatewayBaseConstants.PREDICATE_TYPE == gatewayApiPlugin.getPluginType())
                .forEach(gatewayApiPlugin -> {
                    predicates.add(assemblePredicateDefinition(gatewayApiPlugin.getPluginName(),gatewayApiPlugin.getPluginConfiguration()));
                });
        return predicates;
    }



    private PredicateDefinition assemblePredicateDefinition(String predicateName,String arg) {
        PredicateDefinition predicateDefinition = new PredicateDefinition(predicateName + "=" + arg);
        return predicateDefinition;
    }


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
