package com.test.gateway.service.route;


import com.alibaba.fastjson.JSON;
import com.test.gateway.route.dto.model.TbRouteDO;
import com.test.gateway.route.repository.RouteRepository;
import com.test.gateway.service.model.GatewayRoute;
import com.test.gateway.service.utils.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class RouteDefinitionImpl implements  RouteDefinitionRepository {

    protected static final Logger logger = LoggerFactory.getLogger(RouteDefinitionImpl.class);

    @Autowired
    RouteRepository routeRepository;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> definitionList = new ArrayList<>();
        List<TbRouteDO> tbRouteDOS = routeRepository.getAllRouteConfig();
        for(TbRouteDO tbRouteDO: tbRouteDOS){
            GatewayRoute gatewayRoute = ConvertUtil.convert(tbRouteDO);
            definitionList.add(ConvertUtil.convert(gatewayRoute));
        }
        logger.info("从数据库获取路由结果 {}", JSON.toJSONString(definitionList));
        return Flux.fromIterable(definitionList);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return Mono.empty();
    }
}