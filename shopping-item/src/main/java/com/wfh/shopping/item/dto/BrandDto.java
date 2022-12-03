package com.wfh.shopping.item.dto;

import com.wfh.shopping.common.dto.BaseDto;
import lombok.Data;

@Data
public class BrandDto extends BaseDto {

    private String name;

    private String firstChar;
}
