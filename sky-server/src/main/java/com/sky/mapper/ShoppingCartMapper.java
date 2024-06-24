package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 根据userId清空购物车
     * @param userId 用户ID
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void cleanShoppingCart(Long userId);

    /**
     * 根据前端传递的数据来查询对应的购物车数据
     * @param shoppingCart 前端传递的数据
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 修改购物车数据
     * @param shoppingCartData 购物车数据
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateShoppingCartNumber(ShoppingCart shoppingCartData);

    /**
     * 新增购物车数据
     * @param shoppingCart
     */
    void insert(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where id = #{id}")
    void deleteShoppingCart(ShoppingCart shoppingCartData);
}
