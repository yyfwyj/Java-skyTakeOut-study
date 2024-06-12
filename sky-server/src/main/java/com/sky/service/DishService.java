package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DishService {
    /**
     * 添加菜品
     * @param dishDTO 前端数据体
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页
     * @param dishPageQueryDTO 前端数据体
     * @return Result<PageResult> 分页数据
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    /**
     * 根据菜品ID查询菜品信息
     * @param id 菜品ID
     * @return DishVO 菜品信息
     */
    DishVO getByIdWithFlavor(Long id);

    void update(DishDTO dishDTO);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
