package com.xuuxxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuuxxi.entity.OrderDetail;
import com.xuuxxi.mapper.OrderDetailMapper;
import com.xuuxxi.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/14
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
