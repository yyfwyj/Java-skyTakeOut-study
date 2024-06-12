package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO 前端登录数据
     * @return User 用户信息
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
