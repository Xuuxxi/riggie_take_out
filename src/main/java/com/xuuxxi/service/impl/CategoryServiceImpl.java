package com.xuuxxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuuxxi.common.CustomException;
import com.xuuxxi.entity.Category;
import com.xuuxxi.entity.Dish;
import com.xuuxxi.entity.Setmeal;
import com.xuuxxi.mapper.CategoryMapper;
import com.xuuxxi.service.CategoryService;
import com.xuuxxi.service.DishService;
import com.xuuxxi.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/7
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private DishService dishService;

    @Resource
    private SetmealService setMealService;

    /**
     * 判断当前分类有无关联的菜品或订单
     * @param id
     */
    @Override
    @Transactional
    public void MyRemove(Long id) {
        LambdaQueryWrapper<Dish> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(wrapper1);

        if(count1 > 0){
            //已经关联菜品的业务异常
            throw new CustomException("已经关联菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> wrapper2 = new LambdaQueryWrapper<Setmeal>();
        wrapper2.eq(Setmeal::getCategoryId,id);
        int count2 = setMealService.count(wrapper2);

        if(count2 > 0){
            //已经关联套餐的业务异常
            throw new CustomException("已经关联套餐，不能删除");
        }

        super.removeById(id);
    }
}
