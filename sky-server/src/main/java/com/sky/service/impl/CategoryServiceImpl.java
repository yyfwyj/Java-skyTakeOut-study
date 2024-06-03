package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

//    Mapper依赖注入
    @Autowired
    public CategoryMapper categoryMapper;

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO 分类查询前端数据体
     * @return Result<PageResult> 返回分页列表
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.page(categoryPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 新增分类
     * @param categoryDTO 新增分类前端请求体
     * @return Result 成功响应体
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
//        1. 属性拷贝，使用category实体类传递到Mapper层
        BeanUtils.copyProperties(categoryDTO,category);
//        2. 设置分类状态默认为禁用，0
        category.setStatus(StatusConstant.DISABLE);
//        3. 设置创建人，创建时间，修改人，修改时间
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());
//        4. 调用Mapper方法操作数据库
        categoryMapper.insert(category);
    }

    /**
     * 修改分类
     * @param categoryDTO 新增分类前端请求体
     * @return 成功响应体
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
//        1. new 一个category对象
        Category category = new Category();
//        2. 属性拷贝
        BeanUtils.copyProperties(categoryDTO,category);
//        3. 修改修改人，修改时间
//        category.setUpdateUser(BaseContext.getCurrentId());
//        category.setUpdateTime(LocalDateTime.now());
//        4. 调用Mapper/update方法
        categoryMapper.update(category);

    }

    /**
     * 启用、禁用分类
     * @param status 分类状态
     * @param id 分类ID
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.update(category);
    }
}
