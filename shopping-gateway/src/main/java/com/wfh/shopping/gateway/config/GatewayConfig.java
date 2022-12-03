package com.wfh.shopping.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class GatewayConfig {
    @Bean
    public PasswordEncoder BCEncoder(){
        return new BCryptPasswordEncoder();
    }
}
