package com.wfh.shopping.gateway.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wfh.shopping.pojo.User;
import org.springframework.http.server.ServerHttpResponse;

public interface UserService extends IService<User> {
    Integer login(User user);
}
