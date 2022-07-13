package com.xuuxxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuuxxi.common.R;
import com.xuuxxi.entity.Orders;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/14
 */
public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
}
