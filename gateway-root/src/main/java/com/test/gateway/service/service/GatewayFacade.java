package com.test.gateway.service.service;

import com.test.gateway.service.model.GatewayRoute;

import java.util.List;

public interface GatewayFacade {

    public List<GatewayRoute> getAllRouteConfig();

    public List<GatewayRoute> getRouteConfigByService(String service);

    public boolean createRouteConfig(GatewayRoute gatewayRoute);

    public boolean updateRouteConfig(GatewayRoute gatewayRoute);

    public boolean deleteRouteConfig(Long id);

    public void publish();

}
