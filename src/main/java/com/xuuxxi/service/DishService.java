package com.xuuxxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuuxxi.common.R;
import com.xuuxxi.dto.DishDto;
import com.xuuxxi.entity.Dish;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/7
 */
public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public void removeWithFlavor(Long id);
}
