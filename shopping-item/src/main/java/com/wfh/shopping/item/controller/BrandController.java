package com.wfh.shopping.item.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wfh.shopping.common.data.R;
import com.wfh.shopping.item.dto.BrandDto;
import com.wfh.shopping.item.service.BrandService;
import com.wfh.shopping.item.vo.BrandVo;
import com.wfh.shopping.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @PostMapping
    public R<Boolean> addBrand(@RequestBody Brand brand){
        return R.ok(brandService.addBrand(brand));
    }
    @PutMapping
    public R<Boolean> updateBrand(@RequestBody Brand brand){
        return R.ok(brandService.updateBrand(brand));
    }

    @GetMapping
    public R<Page<BrandVo>> searchBrands(BrandDto brandDto){
        return R.ok(brandService.searchBrands(brandDto));
    }

    @DeleteMapping
    public R<Boolean> deleteBrand(@RequestParam("brandIds") List<String> brandIds){
        return R.ok(brandService.deleteBrand(brandIds));
    }
}
