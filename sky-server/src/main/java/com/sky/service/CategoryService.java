package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO 分类查询前端数据体
     * @return Result<PageResult> 返回分页列表
     */
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     * @param categoryDTO 新增分类前端请求体
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 修改分类
     * @param categoryDTO 新增分类前端请求体
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 启用、禁用分类
     * @param status 分类状态
     * @param id 分类ID
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据类型查询分类
     * @param type 分类类型
     * @return 分类数组
     */
    List<Category> list(Integer type);
}
