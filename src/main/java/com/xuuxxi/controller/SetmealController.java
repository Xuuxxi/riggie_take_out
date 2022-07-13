package com.xuuxxi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuuxxi.common.R;
import com.xuuxxi.dto.SetmealDto;
import com.xuuxxi.entity.Category;
import com.xuuxxi.entity.Setmeal;
import com.xuuxxi.service.CategoryService;
import com.xuuxxi.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/12
 */
@Slf4j
@RequestMapping("/setmeal")
@RestController
public class SetmealController {
    @Resource
    private SetmealService setMealService;
    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("Saving SetMeal...");

        setMealService.saveWithDish(setmealDto);
        return R.success("Setmeal save success!");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo1 = new Page<>();
        Page<SetmealDto> pageInfo2 = new Page<>();

        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null,Setmeal::getName,name);
        wrapper.orderByDesc(Setmeal::getUpdateTime);

        setMealService.page(pageInfo1);

        BeanUtils.copyProperties(pageInfo1,pageInfo2,"records");
        List<Setmeal> records = pageInfo1.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            //根据分类id查询分类对象
            if(category != null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            //分类名称
            return setmealDto;
        }).collect(Collectors.toList());

        pageInfo2.setRecords(list);
        return R.success(pageInfo2);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids = {}",ids);
        setMealService.removeWithDish(ids);
        return R.success("删除套餐信息成功！");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setMealService.list(queryWrapper);

        return R.success(list);
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable int status,@RequestParam List<Long> ids){
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId,ids);
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);

        setMealService.update(setmeal,wrapper);
        return R.success("Update status success !!");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        log.info("/{id}, id = {}",id);
        SetmealDto dto = setMealService.getDto(id);
        return R.success(dto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        log.info("updating set meal ...");
        setMealService.updateDto(setmealDto);
        return R.success("update success!!");
    }
}
