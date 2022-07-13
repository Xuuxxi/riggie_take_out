package com.xuuxxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuuxxi.entity.User;
import com.xuuxxi.mapper.UserMapper;
import com.xuuxxi.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
