package com.wfh.shopping.item.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wfh.shopping.common.data.R;
import com.wfh.shopping.common.dto.BaseDto;
import com.wfh.shopping.item.service.CategoryService;
import com.wfh.shopping.item.vo.CategoryVo;
import com.wfh.shopping.pojo.Brand;
import com.wfh.shopping.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;


@RestController
@RequestMapping("/item")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public R<Boolean> addCategory(@RequestBody Category category){
        return R.ok(categoryService.save(category));
    }

    @GetMapping("/category")
    public R<Page<CategoryVo>> searchCategories(BaseDto baseDto){
        return R.ok(categoryService.searchCategories(baseDto));
    }
    @GetMapping("/{keyword}")
    public R<List<Category>> listCategory(@PathVariable("keyword")String keyword){
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.like("name",keyword);
        wrapper.eq("delete_flag",0);
        return R.ok(categoryService.list(wrapper));
    }

    @PutMapping("/category")
    public R<Boolean> updateCategory(@RequestBody Category category){
        return R.ok(categoryService.updateById(category));
    }

    @DeleteMapping("/category/{categoryId}")
    public R<Boolean> deleteCategory(@PathVariable("categoryId") Integer categoryId){
        //因为是逻辑删除需要mysql函数找出该类所有子类的id
        return R.ok(categoryService.deleteCategory(categoryId));
    }
}
