package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user/user")
@Api(tags = "C端用户相关接口")
@Slf4j
public class UserController {

    // token工具类注入
    @Autowired
    private JwtProperties jwtProperties;

    // 用户service依赖注入
    @Autowired
    private UserService userService;

    /**
     * 微信登录
     * @param userLoginDTO 前端登录数据
     * @return UserLoginVO 用户信息： openid token id
     */
    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信用户登录:{}",userLoginDTO);
        // 用户登录，进行数据查询
        User user = userService.wxLogin(userLoginDTO);

        // 创建JWT令牌
        HashMap<String,Object> claims  = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        // 封装VO对象返回给前端
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .openid(user.getOpenid())
                .token(token)
                .id(user.getId())
                .build();

        return Result.success(userLoginVO);
    }
}
