package com.test.gateway.service.utils;

import com.alibaba.fastjson.JSON;
import com.test.gateway.route.dto.model.TbRouteDO;
import com.test.gateway.service.controller.request.CreateRouteRequest;
import com.test.gateway.service.model.GatewayFilterDefinition;
import com.test.gateway.service.model.GatewayPredicateDefinition;
import com.test.gateway.service.model.GatewayRoute;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConvertUtil {

    public static RouteDefinition convert(GatewayRoute gwdefinition){
        RouteDefinition definition = new RouteDefinition();
        // ID
        definition.setId(String.valueOf(gwdefinition.getId()));
        // Predicates
        List<PredicateDefinition> pdList = new ArrayList<>();
        for (GatewayPredicateDefinition gpDefinition: gwdefinition.getPredicates()) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);
        // Filters
        List<FilterDefinition> fdList = new ArrayList<>();
        if(gwdefinition.getFilters()!=null && gwdefinition.getFilters().size()!=0){
            for (GatewayFilterDefinition gfDefinition: gwdefinition.getFilters()) {
                FilterDefinition filter = new FilterDefinition();
                filter.setArgs(gfDefinition.getArgs());
                filter.setName(gfDefinition.getName());
                fdList.add(filter);
            }
            definition.setFilters(fdList);
        }
        // URI
        URI uri = UriComponentsBuilder.fromUriString(gwdefinition.getUri()).build().toUri();
        definition.setUri(uri);
        definition.setOrder(gwdefinition.getOrder());
        return definition;
    }

    public static GatewayRoute convert(TbRouteDO tbRouteDO){
        GatewayRoute gatewayRoute = new GatewayRoute();
        gatewayRoute.setId(tbRouteDO.getId());
        if(StringUtils.isNoneBlank(tbRouteDO.getFilters())){
            List<GatewayFilterDefinition> filterDefinitions = JSON.parseArray(tbRouteDO.getFilters(), GatewayFilterDefinition.class);
            gatewayRoute.setFilters(filterDefinitions);
        }
        List<GatewayPredicateDefinition> predicateDefinitions = JSON.parseArray(tbRouteDO.getPredicates(), GatewayPredicateDefinition.class);
        gatewayRoute.setPredicates(predicateDefinitions);
        gatewayRoute.setOrder(tbRouteDO.getPriority());
        gatewayRoute.setUri(tbRouteDO.getUri());
        return gatewayRoute;
    }

    public static GatewayRoute convert(CreateRouteRequest request){
        GatewayRoute gatewayRoute = new GatewayRoute();
        gatewayRoute.setUri(request.getUri());
        gatewayRoute.setOrder(request.getPriority());
        gatewayRoute.setService(request.getService());
        gatewayRoute.setMemo(request.getMemo());
        List<GatewayPredicateDefinition> gatewayPredicates = new ArrayList<>();
        Map<String, Map<String, String>> predicates = request.getPredicates();
        for(Map.Entry<String, Map<String, String>> entry : predicates.entrySet()){
            GatewayPredicateDefinition gatewayPredicateDefinition = new GatewayPredicateDefinition();
            gatewayPredicateDefinition.setName(entry.getKey());
            gatewayPredicateDefinition.setArgs(entry.getValue());
            gatewayPredicates.add(gatewayPredicateDefinition);
        }
        gatewayRoute.setPredicates(gatewayPredicates);
        return gatewayRoute;

    }


}
