package com.wfh.shopping.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wfh.shopping.FdfsService;
import com.wfh.shopping.common.dto.BaseDto;
import com.wfh.shopping.item.dto.BrandDto;
import com.wfh.shopping.item.mapper.BrandMapper;
import com.wfh.shopping.item.service.BrandService;
import com.wfh.shopping.item.vo.BrandVo;
import com.wfh.shopping.pojo.Brand;
import org.csource.common.FdfsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    @Autowired
    private FdfsService fdfsService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addBrand(Brand brand) {
        String logo = brand.getLogo();
        //logo信息是base64编码加密的,需要解析
        byte[] decodeLogo = Base64.getDecoder().decode(logo);
        //图片扩展名
        String extension = logo.substring(0,logo.indexOf(";")).replace("data:/image","");
        try {
            //上传至fastDfs
            String[] result = fdfsService.upload(decodeLogo, extension);
            brand.setLogo(String.format("/%s/%s",result[0],result[1]));
            boolean save = this.save(brand);
            if (save){
                //将分类编号和品牌编号存储在另一张表category_brand中
                this.getBaseMapper().addCategoryBrand(brand.getCategoryId(), brand.getId());
            }else {//保存失败删除fdfs里图片
                fdfsService.deleteFile(result[0],result[1]);
            }
            return save;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateBrand(Brand brand) {
        String logo = brand.getLogo();
        UpdateWrapper<Brand> wrapper = new UpdateWrapper<>();
        if (StringUtils.hasLength(logo)){//有logo更新
            byte[] decodeLogo = Base64.getDecoder().decode(logo);
            //图片扩展名
            String extension = logo.substring(0,logo.indexOf(";")).replace("data:/image","");
            Brand dbBrand = getById(brand.getId());
            String fdfsInfo = dbBrand.getLogo().substring(1);
            int index = fdfsInfo.indexOf("/");
            try {
                //在fdfs中先删除以前的文件
                fdfsService.deleteFile(fdfsInfo.substring(0,index),fdfsInfo.substring(index));
                String[] upload = fdfsService.upload(decodeLogo, extension);
                wrapper.set("logo",String.format("/%s/%s",upload[0],upload[1]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        wrapper.set("name",brand.getName());
        wrapper.set("first_char",brand.getFirstChar());
        wrapper.eq("id",brand.getId());
        wrapper.eq("delete_flag",0);
        return update(wrapper);
    }

    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED)
    @Override
    public Page<BrandVo> searchBrands(BrandDto brandDto) {
        Page<BrandVo> page = new Page<>(brandDto.getCurrentPage(), brandDto.getPageSize());
        return this.baseMapper.searchBrands(page,brandDto);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteBrand(List<String> brandIds) {
        UpdateWrapper<Brand> wrapper = new UpdateWrapper<>();
        wrapper.set("delete_flag",1);
        wrapper.in("id",brandIds);
        wrapper.eq("delete_flag",0);
        return update(wrapper);
    }
}
