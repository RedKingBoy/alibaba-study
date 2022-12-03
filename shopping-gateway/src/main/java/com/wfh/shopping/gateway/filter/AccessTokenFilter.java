package com.wfh.shopping.gateway.filter;

import com.wfh.shopping.common.constant.RedisAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
//全局过滤器，访问微服务令牌
public class AccessTokenFilter implements GlobalFilter {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        String token = (String) operations.get(RedisAccessToken.ACCESS_TOKEN);
        if (token == null){
            token = UUID.randomUUID().toString().replace("-","");
            operations.set(RedisAccessToken.ACCESS_TOKEN,token);
        }
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //设置请求头
        ServerHttpRequest identifyRequest = request.mutate().header("identify", token).build();
        return chain.filter(exchange.mutate().request(identifyRequest).response(response).build());
    }
}
