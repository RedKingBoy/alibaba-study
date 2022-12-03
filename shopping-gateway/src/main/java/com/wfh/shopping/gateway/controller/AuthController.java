package com.wfh.shopping.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.wfh.shopping.JwtService;
import com.wfh.shopping.common.data.R;
import com.wfh.shopping.gateway.service.UserService;
import com.wfh.shopping.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/account/login")
    public R<Integer> login(@RequestBody User user,ServerHttpResponse response){
        Integer result = userService.login(user);
        if (result == 1){
            //生成token;
            String token = jwtService.getToken((JSONObject) JSONObject.toJSON(user));
            //生成响应头
            response.getHeaders().add("access_token",token);
        }
        return R.ok(result);
    }
}
