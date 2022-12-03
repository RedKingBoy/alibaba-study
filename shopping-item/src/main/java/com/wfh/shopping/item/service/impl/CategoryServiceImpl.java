package com.wfh.shopping.item.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wfh.shopping.common.dto.BaseDto;
import com.wfh.shopping.item.mapper.CategoryMapper;
import com.wfh.shopping.item.service.CategoryService;
import com.wfh.shopping.item.vo.CategoryVo;
import com.wfh.shopping.pojo.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED)
    @Override
    public Page<CategoryVo> searchCategories(BaseDto baseDto) {
        Page<CategoryVo> page = new Page<>(baseDto.getCurrentPage(), baseDto.getPageSize());
        return this.baseMapper.searchCategories(page,0);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteCategory(Integer categoryId) {
        return this.baseMapper.deleteCategory(categoryId);
    }
}
