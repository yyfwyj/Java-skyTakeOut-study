package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    // 微信登录服务请求地址
    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    // 注入微信常量类
    @Autowired
    private WeChatProperties weChatProperties;

    // 注入Mapper
    @Autowired
    private UserMapper userMapper;

    /**
     * 微信登录
     * @param userLoginDTO 前端登录数据
     * @return User 用户信息
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());
        // 判断openid是否为空，如果openid为空，则代表登录失败
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        // 通过openid查询数据库中是否有对应的用户信息
        User user = userMapper.getByOpenid(openid);

        // 如果用户为空，证明这是一个新用户，我们需要封装User对象插入至User表
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }


    /**
     * 调取微信登录接口服务，获取微信用户openid
     * @param code 用户code
     * @return openid 返回用户openid
     */
    public String getOpenid(String code) {
        // 封装请求参数
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", weChatProperties.getAppid());
        paramMap.put("secret", weChatProperties.getSecret());
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");
        // 调用服务地址，将请求地址与参数Map集合传入方法中进行请求
        String wxJson = HttpClientUtil.doGet(WX_LOGIN, paramMap);
        // 解析接口返回的JSON数据
        JSONObject wxParse = JSON.parseObject(wxJson);
        // 获取Openid
        String openid = wxParse.getString("openid");
        return openid;
    }
}
