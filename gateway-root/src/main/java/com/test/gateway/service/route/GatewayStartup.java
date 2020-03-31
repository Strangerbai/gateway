package com.test.gateway.service.route;

import com.alibaba.fastjson.JSON;
import com.test.gateway.route.dto.model.TbRouteDO;
import com.test.gateway.route.repository.RouteRepository;
import com.test.gateway.service.model.GatewayRoute;
import com.test.gateway.service.service.GatewayService;
import com.test.gateway.service.utils.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GatewayStartup {

    protected static final Logger logger = LoggerFactory.getLogger(GatewayStartup.class);

    @Autowired
    private GatewayService gatewayService;


    @Autowired
    RouteRepository routeRepository;


    @Bean
    public GatewayStartup createApplicationStartup() {
        return new GatewayStartup();
    }

    public void start() {
        List<RouteDefinition> routeDefinitionList = new ArrayList<>();
        List<TbRouteDO> tbRouteDOS = routeRepository.getAllRouteConfig();
        for(TbRouteDO tbRouteDO: tbRouteDOS){
            GatewayRoute gatewayRoute = ConvertUtil.convert(tbRouteDO);
            routeDefinitionList.add(ConvertUtil.convert(gatewayRoute));
        }
        logger.info("路由初始化配置 {}", JSON.toJSONString(routeDefinitionList));
        gatewayService.publish();
    }

}
