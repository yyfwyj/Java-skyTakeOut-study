package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

     /**
      * 根据菜品ID查询对应的套餐ID
      * @param dishIds 菜品ID集合
      * @return List<Long> 套餐ID集合
      */
     List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
