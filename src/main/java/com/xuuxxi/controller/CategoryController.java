package com.xuuxxi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuuxxi.common.R;
import com.xuuxxi.entity.Category;
import com.xuuxxi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/7
 */

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category = {}",category) ;
        categoryService.save(category);
        return R.success("添加分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo,wrapper);

        return R.success(pageInfo);
    }

    /**
     * 这个巨坑，前端传参是 ids 不是 id，所以这边对应的接收参数要改成 ids 否则读不到 id
     * 打日志真的很有必要，F12 检测前端网络活动也是很有效的手段
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除 id = {}",ids);

        categoryService.MyRemove(ids);
        return R.success("分类信息删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info(category.toString());

        categoryService.updateById(category);

        return R.success("修改分类成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(category.getType() != null,Category::getType,category.getType());
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(wrapper);

        return R.success(list);
    }
}
