package com.xuuxxi.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.xuuxxi.common.R;
import com.xuuxxi.dto.DishDto;
import com.xuuxxi.entity.Category;
import com.xuuxxi.entity.Dish;
import com.xuuxxi.entity.DishFlavor;
import com.xuuxxi.service.CategoryService;
import com.xuuxxi.service.DishFlavorService;
import com.xuuxxi.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/9
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Resource
    private DishService dishService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private DishFlavorService dishFlavorService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("dish 保存 success");
    }

    @GetMapping("/page")
    public R<Page>page(int page,int pageSize,String name){
        Page<Dish> pageInfo1 = new Page<>(page, pageSize);
        Page<DishDto> pageInfo2 = new Page<>(page, pageSize);

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        if(name != null) wrapper.like(Dish::getName,name);
        wrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo1,wrapper);

        BeanUtils.copyProperties(pageInfo1,pageInfo2,"records");

        List<Dish> records = pageInfo1.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        pageInfo2.setRecords(list);
        return R.success(pageInfo2);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info("update dishDto ...");
        dishService.updateWithFlavor(dishDto);

        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);

        return R.success("update done...");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatue(@PathVariable int status,@RequestParam List<Long> ids){
        log.info("status = {},ids = {}",status,ids);

        for(Long id : ids){
            Dish dish = new Dish();
            dish.setId(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }

        return R.success("修改状态成功！");
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("delete dish...");
        dishService.removeWithFlavor(ids);
        return R.success("remove success !");
    }

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        List<DishDto> dishDtoList = null;

        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();

        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        if(dishDtoList != null){
            return R.success(dishDtoList);
        }

        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);
        return R.success(dishDtoList);
    }
}
