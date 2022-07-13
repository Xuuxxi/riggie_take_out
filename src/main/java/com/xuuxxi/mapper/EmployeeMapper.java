package com.xuuxxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuuxxi.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/3
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
