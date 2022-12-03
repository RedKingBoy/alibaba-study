package com.wfh.shopping.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wfh.shopping.item.dto.BrandDto;
import com.wfh.shopping.item.vo.BrandVo;
import com.wfh.shopping.pojo.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrandMapper extends BaseMapper<Brand> {
    int addCategoryBrand(@Param("categoryId") Integer categoryId,@Param("id") Long id);

    Page<BrandVo> searchBrands(Page<BrandVo> page, @Param("brandDto") BrandDto brandDto);
}
