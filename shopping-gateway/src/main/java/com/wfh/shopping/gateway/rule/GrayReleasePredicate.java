package com.wfh.shopping.gateway.rule;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.loadbalancer.Server;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

//灰度发布的条件判断
@Component
public class GrayReleasePredicate extends AbstractServerPredicate {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private final String GRAY_RELEASE_KEY = "grayRelease";
    private final Random random = new Random();

    //会对每一个服务器进行判断
    @Override
    public boolean apply(@Nullable PredicateKey predicateKey) {
        Server server = predicateKey.getServer();
        if (server instanceof NacosServer) {
            Instance instance = ((NacosServer) server).getInstance();
            //获取服务版本
            String version = ((NacosServer) server).getMetadata().get("version");
            //获取服务名(组名@@服务名)
            String serviceName = instance.getServiceName();
            HashOperations<String, Object, Object> operations = redisTemplate.opsForHash();
            Object redisVersion = operations.get(GRAY_RELEASE_KEY, serviceName);
            if (redisVersion != null) {
                if (Objects.equals(version, redisVersion.toString())) {
                    return random.nextInt(100) < 20;
                }
            }
            return true;
        }
        return false;
    }
}
