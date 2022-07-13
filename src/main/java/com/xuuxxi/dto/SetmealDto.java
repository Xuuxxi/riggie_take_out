package com.xuuxxi.dto;

import com.xuuxxi.entity.Setmeal;
import com.xuuxxi.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/13
 */
@Data
public class SetmealDto extends Setmeal {
    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
