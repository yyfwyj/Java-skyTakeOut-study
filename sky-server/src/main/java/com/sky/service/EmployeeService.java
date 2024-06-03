package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO 员工登录DTO类
     * @return Employee
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO 前端传递的新增员工数据体
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO 前端传递的分页查询请求体
     * @return PageResult 返回员工查询列表
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);


    /**
     * 将员工账号启用/禁用
     * @param status 员工账号状态
     * @param id 员工ID
     */
    void startOrstop(Integer status, Long id);

    /**
     * 根据员工ID查询员工
     * @param id 员工ID
     * @return Employee 员工信息
     */
    Employee getEmployeeById(Long id);

    /**
     * 修改员工
     * @param employeeDTO 员工数据体
     */
    void update(EmployeeDTO employeeDTO);
}
