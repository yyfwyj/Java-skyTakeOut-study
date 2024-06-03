package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AutoFill 用于标识某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD) // 表明注解只能添加在方法上
@Retention(RetentionPolicy.RUNTIME) // 表明该注解在代码运行时可以被访问、保留
public @interface AutoFill {
//    数据库操作类型:INSERT、UPDATE
//    为什么不设置SELECT DELETE呢？因为这两个类型不需要操作公共字段 createTime、createUser、updateTime、updateUser
    OperationType value();
}
