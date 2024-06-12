package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;

    // 2. 注入redisTemplate对象，方便我们后期操作redis
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        // 1. 构造redis中的key，方便我们查询菜品：dish_ 分类 id
        String key = "dish_" + categoryId;
        // 3. 查询 redis 中是否存在缓存的菜品数据
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        // 4. 如果存在，直接在controller层返回，无需查询数据库
        if(list != null && !list.isEmpty()) {
           return Result.success(list);
        }

        // 5. 如果不存在，查询数据库，将查询到的数据库放入 redis 中。
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品
        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key,list);

        return Result.success(list);
    }

}
