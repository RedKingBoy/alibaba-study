package com.wfh.shopping.item.vo;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVo {
    private Integer id;

    private String name;

    private List<CategoryVo> children;
}
