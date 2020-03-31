package com.test.gateway.route.repository;

import com.test.gateway.route.dto.model.TbRouteDO;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RouteRepository {

    List<TbRouteDO> getAllRouteConfig();

    List<TbRouteDO> getRouteByService(String service);

    List<TbRouteDO> getRouteByUri(String uri);

    TbRouteDO getRouteById(Long id);

    boolean createRouteConfig(TbRouteDO record);

    boolean updateRouteConfigById(TbRouteDO record);

    boolean deleteRouteConfigById(Long id);

}
