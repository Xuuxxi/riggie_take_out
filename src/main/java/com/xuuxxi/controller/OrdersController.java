package com.xuuxxi.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuuxxi.common.R;
import com.xuuxxi.entity.Orders;
import com.xuuxxi.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/14
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrdersController {
    @Resource
    private OrdersService ordersService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,Long number){
        log.info("order page...");
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(number != null,Orders::getNumber,number);

        ordersService.page(pageInfo,wrapper);
        return R.success(pageInfo);
    }

    @PostMapping("/submit")
    public R<String> order(@RequestBody Orders orders){
        log.info("orders info = {}",orders);
        ordersService.submit(orders);
        return R.success("订单提交成功");
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page,int pageSize){
        log.info("user page info ...");
        Page<Orders> pageInfo = new Page<>();
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Orders::getOrderTime);

        ordersService.page(pageInfo,wrapper);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(@RequestBody Orders orders){
        log.info("update orders...");
        ordersService.updateById(orders);
        return R.success("订单信息更新成功！");
    }

    @PostMapping("/again")
    public R<Orders> again(@RequestBody Orders orders){
        log.info("again order...");
        orders = ordersService.getById(orders);
        Orders or = new Orders();
        BeanUtils.copyProperties(orders,or,"id");
        return R.success(or);
    }
}
