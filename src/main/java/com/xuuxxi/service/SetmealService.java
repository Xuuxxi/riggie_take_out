package com.xuuxxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuuxxi.dto.SetmealDto;
import com.xuuxxi.entity.Setmeal;

import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/7
 */
public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);

    public SetmealDto getDto(Long id);

    public void updateDto(SetmealDto setmealDto);
}
