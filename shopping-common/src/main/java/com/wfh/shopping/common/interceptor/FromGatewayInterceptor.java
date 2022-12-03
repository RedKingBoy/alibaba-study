package com.wfh.shopping.common.interceptor;

import com.wfh.shopping.common.constant.RedisAccessToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FromGatewayInterceptor implements HandlerInterceptor {
    private RedisTemplate<String,Object> redisTemplate;

    public FromGatewayInterceptor(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("identify");
        if (StringUtils.hasLength(header)){
            String redisStr = (String) redisTemplate.opsForValue().get(RedisAccessToken.ACCESS_TOKEN);
            return header.equals(redisStr);
        }
        return false;
    }
}
