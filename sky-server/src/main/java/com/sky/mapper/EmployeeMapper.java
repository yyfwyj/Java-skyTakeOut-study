package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username 用户名
     * @return Employee 返回员工信息
     */
    @Select("select id, name, username,password, phone, sex, id_number, status, create_time, update_time, create_user, update_user from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 根据员工ID查询员工
     * @return Employee 员工信息
     */
    @Select("select id, name, username, phone, sex, id_number, status, create_time, update_time, create_user, update_user from employee where id = #{id}")
    Employee getEmployeeById(Long id);

    /**
     * 插入员工数据
     * @param employee 员工数据体
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("INSERT into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user,status) " +
            "values" +
            "(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser},#{status})")
    void insert(Employee employee);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO 前端传递的分页查询请求体
     * @return PageResult 返回员工查询列表
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改员工数据
     * @param employee 员工数据体
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);
}
