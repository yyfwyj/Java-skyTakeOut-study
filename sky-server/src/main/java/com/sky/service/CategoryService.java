package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

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
     * @return Result 成功响应体
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 修改分类
     * @param categoryDTO 新增分类前端请求体
     * @return 成功响应体
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 启用、禁用分类
     * @param status 分类状态
     * @param id 分类ID
     * @return 成功响应体
     */
    void startOrStop(Integer status, Long id);
}
