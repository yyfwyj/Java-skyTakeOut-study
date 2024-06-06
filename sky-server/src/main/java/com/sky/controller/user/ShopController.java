package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "用户端店铺相关接口")
@Slf4j
public class ShopController {

    // 注入依赖 redisTemplate
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String SHOP_STATUS = "SHOP_STATUS";

    /**
     * 管理端获取店铺状态
     */
    @GetMapping("/status")
    @ApiOperation("用户端获取店铺状态")
    public Result<Integer> getStatus() {
        Integer SHOP_STATUS = (Integer) redisTemplate.opsForValue().get(ShopController.SHOP_STATUS);
        log.info("用户端获取店铺状态：{}",SHOP_STATUS == 1 ? "营业":"打样");
        return  Result.success(SHOP_STATUS);
    }
}
