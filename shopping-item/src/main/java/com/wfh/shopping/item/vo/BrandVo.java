package com.wfh.shopping.item.vo;

import com.wfh.shopping.pojo.Category;
import lombok.Data;

@Data
public class BrandVo {

    private Long id;

    private String name;

    private String firstChar;

    private String logo;

    private Category category;
}
