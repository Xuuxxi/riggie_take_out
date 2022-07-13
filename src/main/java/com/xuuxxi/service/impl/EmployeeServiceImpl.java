package com.xuuxxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuuxxi.entity.Employee;
import com.xuuxxi.mapper.EmployeeMapper;
import com.xuuxxi.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/3
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
