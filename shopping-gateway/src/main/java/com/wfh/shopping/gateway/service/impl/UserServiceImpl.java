package com.wfh.shopping.gateway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wfh.shopping.gateway.mapper.UserMapper;
import com.wfh.shopping.gateway.service.UserService;
import com.wfh.shopping.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED)
    @Override
    public Integer login(User user) {
        User searchUser = this.getById(user.getUsername());
        if (searchUser == null) return -1;
        return passwordEncoder.matches(user.getPassword(),searchUser.getPassword())?1:0;
    }
}
