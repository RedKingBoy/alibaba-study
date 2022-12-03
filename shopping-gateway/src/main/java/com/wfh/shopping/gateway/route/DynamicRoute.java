package com.wfh.shopping.gateway.route;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.wfh.shopping.gateway.properties.DynamicRouteProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@EnableConfigurationProperties(DynamicRouteProperties.class)
//spring提供的InitializingBean的接口.会在实现类上创建bean及赋值完成后调用afterPropertiesSet方法
public class DynamicRoute implements ApplicationEventPublisherAware, InitializingBean {
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private DynamicRouteProperties routeProperties;
    @Autowired//路由定义写入器
    private RouteDefinitionWriter writer;
    private List<Object> routeIds;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties properties = new Properties();
        //配置拉取配置的信息
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, routeProperties.getServerAddr());
        properties.setProperty(PropertyKeyConst.NAMESPACE, routeProperties.getNameSpace());
        ConfigService configService = NacosFactory.createConfigService(properties);
        String config = configService.getConfig(routeProperties.getDataId(), routeProperties.getGroup(), 5000);
        refreshRoutes(config);
        configService.addListener(routeProperties.getDataId(), routeProperties.getGroup(), new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override//监听配置热更新
            public void receiveConfigInfo(String s) {
                //更新路由
                if (routeIds != null) {//删除之前的路由信息
                    routeIds.forEach(id->{
                        writer.delete(Mono.just(id.toString())).subscribe();
                    });
                }
                refreshRoutes(s);
            }
        });
    }

    public void refreshRoutes(String config) {
        //解析配置字符串
        List<RouteDefinition> routeDefinitions = JSONArray.parseArray(config, RouteDefinition.class);
        //存储更新的路由id
        routeIds = routeDefinitions.stream().map((Function<RouteDefinition, Object>) RouteDefinition::getId).collect(Collectors.toList());
        routeDefinitions.forEach(routeDefinition -> {
            //写入存储器,记录路由信息
            writer.save(Mono.just(routeDefinition)).subscribe();
        });
        //发布器进行路由更新
        eventPublisher.publishEvent(new RefreshRoutesEvent(writer));
    }
}
