package com.wfh.shopping.config;

import com.wfh.shopping.JwtService;
import com.wfh.shopping.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(JwtService.class)//条件装配
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
    @Autowired
    private JwtProperties properties;
    @Bean
    @ConditionalOnMissingBean
    public JwtService jwtService(){
        return new JwtService(properties);
    }
}
