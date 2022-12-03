package com.wfh.shopping.item.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wfh.shopping.common.dto.BaseDto;
import com.wfh.shopping.item.vo.CategoryVo;
import com.wfh.shopping.pojo.Category;

public interface CategoryService extends IService<Category> {
    Page<CategoryVo> searchCategories(BaseDto baseDto);

    Boolean deleteCategory(Integer categoryId);
}
