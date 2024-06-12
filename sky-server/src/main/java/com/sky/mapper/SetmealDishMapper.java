package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
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

     /**
      * 批量保存套餐和菜品的关联关系
      *
      * @param setmealDishes
      */
     void insertBatch(List<SetmealDish> setmealDishes);

     /**
      * 根据套餐id删除套餐和菜品的关联关系
      *
      * @param setmealId
      */
     @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
     void deleteBySetmealId(Long setmealId);
}
