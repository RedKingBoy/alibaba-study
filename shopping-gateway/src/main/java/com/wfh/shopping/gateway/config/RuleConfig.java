package com.wfh.shopping.gateway.config;

import com.netflix.loadbalancer.IRule;
import com.wfh.shopping.gateway.rule.GrayReleasePredicate;
import com.wfh.shopping.gateway.rule.GrayReleaseRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleConfig {
    @Autowired
    private GrayReleasePredicate grayReleasePredicate;
    @Bean//灰度发布负载均衡,没有就随机
    public IRule grayReleaseRule(){
        return new GrayReleaseRule(grayReleasePredicate);
    }
}
