package com.xuuxxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuuxxi.common.CustomException;
import com.xuuxxi.common.R;
import com.xuuxxi.dto.SetmealDto;
import com.xuuxxi.entity.Setmeal;
import com.xuuxxi.entity.SetmealDish;
import com.xuuxxi.mapper.SetMealMapper;
import com.xuuxxi.service.SetmealService;
import com.xuuxxi.service.SetmealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/7
 */
@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetmealService {
    @Resource
    private SetmealDishService setmealDishService;

    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> list = setmealDto.getSetmealDishes();
        list = list.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(list);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId,ids);
        wrapper.eq(Setmeal::getStatus,1);

        int count = this.count(wrapper);

        if(count > 0){
            throw new CustomException("正在售卖中");
        }

        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.in(SetmealDish::getSetmealId,ids);

        setmealDishService.remove(wrapper2);
    }

    @Override
    @Transactional
    public SetmealDto getDto(Long id) {
        Setmeal setmeal = this.getById(id);
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(wrapper);

        SetmealDto setmealDto = new SetmealDto();

        BeanUtils.copyProperties(setmeal,setmealDto);

        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    @Override
    @Transactional
    public void updateDto(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());

        List<SetmealDish> list = setmealDto.getSetmealDishes();

        setmealDishService.remove(wrapper);

        list = list.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(list);
    }
}
