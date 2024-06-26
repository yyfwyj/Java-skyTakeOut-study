package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "菜品相关接口")
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加菜品
     * @param dishDTO 前端数据体
     */
    @PostMapping
    @ApiOperation("添加菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info("添加菜品:{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);

        // 清理redis缓存数据
        // 先构造Redis key
        String redisKey = "dish_" + dishDTO.getCategoryId();
        redisTemplate.delete(redisKey);
        return Result.success();
    }

    /**
     * 菜品分页
     * @param dishPageQueryDTO 前端数据体
     * @return Result<PageResult> 分页数据
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除菜品
     * @param ids 菜品ids
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result<String> delete(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);

        // 删除以dish_开头的所有缓存数据
        deleteRedisData("dish_*");

        return Result.success();
    }

    /**
     * 根据菜品ID查询菜品信息
     * @param id 菜品ID
     * @return DishVO 菜品信息
     */
    @GetMapping("/{id}")
    @ApiOperation("根据菜品ID查询菜品信息")
    public Result<DishVO> getById(@PathVariable Long id) {
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result<String> update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);

        // 删除以dish_开头的所有缓存数据
        deleteRedisData("dish_*");

        return Result.success();
    }

    public void deleteRedisData(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
