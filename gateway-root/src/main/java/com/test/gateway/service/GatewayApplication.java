package com.test.gateway.service;


import com.test.gateway.service.filter.RequestGlobalFilterProcess;
import com.test.gateway.service.route.GatewayStartup;
import com.test.gateway.service.route.RouteDefinitionImpl;
import com.test.gateway.service.filter.ResponseGlobalFilterProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.EventListener;

/**
 * @author baijianbiao
 */
@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class GatewayApplication extends SpringBootServletInitializer {
    @Autowired
    GatewayStartup gatewayStartup;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GatewayApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteDefinitionImpl routeRepository(){
        return new RouteDefinitionImpl();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startup() {
        gatewayStartup.start();
    }

    @Bean
    public RequestGlobalFilterProcess requestGlobalFilter(){
        return new RequestGlobalFilterProcess();
    }

    @Bean
    public ResponseGlobalFilterProcess responseGlobalFilter(){
        return new ResponseGlobalFilterProcess();
    }

}
