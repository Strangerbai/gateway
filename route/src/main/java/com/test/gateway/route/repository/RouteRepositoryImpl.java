package com.test.gateway.route.repository;

import com.test.gateway.route.dto.mapper.TbRouteDOMapper;
import com.test.gateway.route.dto.model.TbRouteDO;
import com.test.gateway.route.dto.model.TbRouteDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouteRepositoryImpl implements RouteRepository{

    @Autowired
    TbRouteDOMapper tbRouteDOMapper;

    @Override
    public List<TbRouteDO> getAllRouteConfig() {
        TbRouteDOExample example = new TbRouteDOExample();
        example.createCriteria().andIdGreaterThan(0l);
        return tbRouteDOMapper.selectByExample(example);
    }

    @Override
    public List<TbRouteDO> getRouteByService(String service) {
        TbRouteDOExample example = new TbRouteDOExample();
        example.createCriteria().andServiceEqualTo(service);
        return tbRouteDOMapper.selectByExample(example);
    }

    @Override
    public List<TbRouteDO> getRouteByUri(String uri) {
        TbRouteDOExample example = new TbRouteDOExample();
        example.createCriteria().andUriEqualTo(uri);
        return tbRouteDOMapper.selectByExample(example);
    }

    @Override
    public TbRouteDO getRouteById(Long id) {
        return tbRouteDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean createRouteConfig(TbRouteDO record) {
        return tbRouteDOMapper.insertSelective(record)==1;
    }

    @Override
    public boolean updateRouteConfigById(TbRouteDO record) {
        return tbRouteDOMapper.updateByPrimaryKeySelective(record)==1;
    }

    @Override
    public boolean deleteRouteConfigById(Long id) {
        return tbRouteDOMapper.deleteByPrimaryKey(id)==1;
    }
}
