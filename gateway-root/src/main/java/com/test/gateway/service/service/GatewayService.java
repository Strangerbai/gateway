package com.test.gateway.service.service;

import com.alibaba.fastjson.JSON;
import com.test.gateway.route.dto.model.TbRouteDO;
import com.test.gateway.route.repository.RouteRepository;
import com.test.gateway.service.model.GatewayRoute;
import com.test.gateway.service.utils.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class GatewayService implements ApplicationEventPublisherAware, GatewayFacade {

    protected static final Logger logger = LoggerFactory.getLogger(GatewayService.class);

    @Autowired
    RouteRepository routeRepository;

    private ApplicationEventPublisher publisher;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public List<GatewayRoute> getAllRouteConfig() {
        List<TbRouteDO> tbRouteDOS = routeRepository.getAllRouteConfig();
        List<GatewayRoute> gatewayRouteList = tbRouteDOS.stream().map(e -> {
            GatewayRoute gatewayRoute;
            gatewayRoute = ConvertUtil.convert(e);
            return gatewayRoute;
        }).collect(toList());
        return gatewayRouteList;
    }

    @Override
    public List<GatewayRoute> getRouteConfigByService(String service) {
        List<TbRouteDO> tbRouteDOS = routeRepository.getRouteByService(service);
        List<GatewayRoute> gatewayRouteList = tbRouteDOS.stream().map(e -> {
            GatewayRoute gatewayRoute;
            gatewayRoute = ConvertUtil.convert(e);
            return gatewayRoute;
        }).collect(toList());
        return gatewayRouteList;
    }

    @Override
    public boolean createRouteConfig(GatewayRoute gatewayRoute) {
        TbRouteDO tbRouteDO = new TbRouteDO();
        tbRouteDO.setId(gatewayRoute.getId());
        tbRouteDO.setMemo(gatewayRoute.getMemo());
        if(gatewayRoute.getFilters()!=null && gatewayRoute.getFilters().size()!=0){
            tbRouteDO.setFilters(JSON.toJSONString(gatewayRoute.getFilters()));
        }
        tbRouteDO.setPredicates(JSON.toJSONString(gatewayRoute.getPredicates()));
        tbRouteDO.setService(gatewayRoute.getService());
        tbRouteDO.setPriority(gatewayRoute.getOrder());
        tbRouteDO.setUri(gatewayRoute.getUri());
        return routeRepository.createRouteConfig(tbRouteDO);
    }

    @Override
    public boolean updateRouteConfig(GatewayRoute gatewayRoute) {
        TbRouteDO tbRouteDO = new TbRouteDO();
        tbRouteDO.setId(gatewayRoute.getId());
        if(gatewayRoute.getMemo()!=null){
            tbRouteDO.setMemo(gatewayRoute.getMemo());
        }
        if(gatewayRoute.getFilters()!=null && gatewayRoute.getFilters().size()!=0){
            tbRouteDO.setFilters(JSON.toJSONString(gatewayRoute.getFilters()));
        }
        if(gatewayRoute.getPredicates()!=null && gatewayRoute.getPredicates().size()!=0){
            tbRouteDO.setPredicates(JSON.toJSONString(gatewayRoute.getPredicates()));
        }
        if(gatewayRoute.getOrder()!=null){
            tbRouteDO.setPriority(gatewayRoute.getOrder());
        }
        if(gatewayRoute.getService()!=null){
            tbRouteDO.setService(gatewayRoute.getService());
        }
        if(gatewayRoute.getUri()!=null){
            tbRouteDO.setUri(gatewayRoute.getUri());
        }
        return routeRepository.updateRouteConfigById(tbRouteDO);
    }

    @Override
    public boolean deleteRouteConfig(Long id) {
        return routeRepository.deleteRouteConfigById(id);
    }

    @Override
    public void publish(){
        logger.info("刷新内存路由信息");
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
