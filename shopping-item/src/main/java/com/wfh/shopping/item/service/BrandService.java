package com.wfh.shopping.item.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wfh.shopping.item.dto.BrandDto;
import com.wfh.shopping.item.vo.BrandVo;
import com.wfh.shopping.pojo.Brand;

import java.util.List;

public interface BrandService extends IService<Brand> {
    Boolean addBrand(Brand brand);

    Boolean updateBrand(Brand brand);

    Page<BrandVo> searchBrands(BrandDto brandDto);

    Boolean deleteBrand(List<String> brandIds);
}
