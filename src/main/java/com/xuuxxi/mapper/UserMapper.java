package com.xuuxxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuuxxi.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
