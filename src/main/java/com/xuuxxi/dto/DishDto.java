package com.xuuxxi.dto;

import com.xuuxxi.entity.Dish;
import com.xuuxxi.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/9
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
