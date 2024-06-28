package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 通过openid查询用户
     * @param openid 微信用户唯一标识
     * @return User 返回用户对象
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 插入用户数据
     * @param user 用户信息
     */
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User getById(long userId);

//    @Select("select * from user where ;")
//    void getByDateAll(LocalDate date);

    Integer countByMap(Map map);
}
