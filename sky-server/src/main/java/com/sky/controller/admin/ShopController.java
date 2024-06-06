package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "管理端店铺相关接口")
@Slf4j
public class ShopController {

    // 注入依赖 redisTemplate
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String SHOP_STATUS = "SHOP_STATUS";

    /**
     * 管理端设置店铺状态
     * @param status 店铺状态 1 营业中 0 打样中
     */
    @PutMapping("/{status}")
    @ApiOperation("管理端设置店铺状态")
    public Result<String> setStatus(@PathVariable Integer status) {
        log.info("管理端设置店铺状态：{}",status == 1 ? "营业":"打样");
        redisTemplate.opsForValue().set(SHOP_STATUS,status);
        return Result.success();
    }

    /**
     * 管理端获取店铺状态
     */
    @GetMapping("/status")
    @ApiOperation("管理端获取店铺状态")
    public Result<Integer> getStatus() {
        Integer SHOP_STATUS = (Integer) redisTemplate.opsForValue().get(ShopController.SHOP_STATUS);
        log.info("管理端获取店铺状态：{}",SHOP_STATUS == 1 ? "营业":"打样");
        return  Result.success(SHOP_STATUS);
    }
}
