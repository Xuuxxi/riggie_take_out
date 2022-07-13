package com.xuuxxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuuxxi.entity.Category;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/7
 */
public interface CategoryService extends IService<Category> {
    public void MyRemove(Long ids);
}
