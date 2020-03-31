package com.test.gateway.service.controller;

import com.alibaba.fastjson.JSON;
import com.test.gateway.service.controller.request.CreateRouteRequest;
import com.test.gateway.service.model.GatewayResult;
import com.test.gateway.service.model.GatewayRoute;
import com.test.gateway.service.service.GatewayFacade;
import com.test.gateway.service.utils.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gateway")
public class GatewayController {

    protected static final Logger logger = LoggerFactory.getLogger(GatewayController.class);

    @Autowired
    GatewayFacade gatewayFacade;

    @RequestMapping(value = "/create")
    public GatewayResult<Boolean> createRouteConfig(@RequestBody CreateRouteRequest request){
        logger.info("创建路由配置 {}", JSON.toJSON(request));
        GatewayResult<Boolean> result = new GatewayResult<>();
        try{
            GatewayRoute route = ConvertUtil.convert(request);
            boolean createResult = gatewayFacade.createRouteConfig(route);
            result.setT(createResult);
            if(createResult){
                gatewayFacade.publish();
                result.setSuccess(true);
                return result;
            }
        } catch (Exception e){
            logger.error("创建路由失败 {}", e.toString());
        }
        result.setSuccess(false);
        return result;
    }

    @RequestMapping(value = "/update")
    public GatewayResult<Boolean> updateRouteConfig(@RequestBody GatewayRoute route){
        GatewayResult<Boolean> result = new GatewayResult<>();
        try{
            boolean updateResult = gatewayFacade.updateRouteConfig(route);
            result.setT(updateResult);
            if(updateResult){
                gatewayFacade.publish();
                result.setSuccess(true);
                return result;
            }
        } catch (Exception e){
            logger.error("更新路由失败 {}", e.toString());
        }
        result.setSuccess(false);
        return result;
    }

    @RequestMapping(value = "/delete")
    public GatewayResult<Boolean> deleteRouteConfig(@RequestParam(name = "id") String id){
        GatewayResult<Boolean> result = new GatewayResult<>();
        try{
            boolean deleteResult = gatewayFacade.deleteRouteConfig(Long.valueOf(id));
            result.setT(deleteResult);
            if(deleteResult){
                gatewayFacade.publish();
                result.setSuccess(true);
                return result;
            }
        } catch (Exception e){
            logger.error("删除路由失败 {}", e.toString());
        }
        result.setSuccess(false);
        return result;
    }

    @RequestMapping(value = "/getRouteConfig")
    public GatewayResult<List<GatewayRoute>>  getAllRoute(@RequestParam(name = "queryType") String queryType){
        GatewayResult<List<GatewayRoute>> result = new GatewayResult<>();
        List<GatewayRoute> gatewayRoutes = new ArrayList<>();
        try{
            if(queryType.equals("all")){
                gatewayRoutes =  gatewayFacade.getAllRouteConfig();
            }else{
                gatewayRoutes = gatewayFacade.getRouteConfigByService(queryType);
            }
            result.setT(gatewayRoutes);
            result.setSuccess(true);
        } catch (Exception e){
            logger.error("查询路由失败 {}", e.toString());
            result.setSuccess(false);
        }
        return result;
    }


    @RequestMapping(value = "/publish")
    public @ResponseBody String publish(){
        try{
            gatewayFacade.publish();
        } catch (Exception e){
            logger.error("刷新路由失败");
        }
        return "publish success";
    }

}
