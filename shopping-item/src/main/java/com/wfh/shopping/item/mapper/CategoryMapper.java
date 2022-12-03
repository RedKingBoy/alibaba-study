package com.wfh.shopping.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wfh.shopping.item.vo.CategoryVo;
import com.wfh.shopping.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    Page<CategoryVo> searchCategories(Page<CategoryVo> page, @Param("parentId") Integer parentId);

    Boolean deleteCategory(Integer categoryId);
}
