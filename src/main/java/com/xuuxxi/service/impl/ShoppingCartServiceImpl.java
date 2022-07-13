package com.xuuxxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuuxxi.entity.ShoppingCart;
import com.xuuxxi.mapper.ShoppingCartMapper;
import com.xuuxxi.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/14
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
