package com.wfh.shopping.common.dto;

import lombok.Data;

@Data
public class BaseDto {
    protected Integer currentPage;
    
    protected Integer pageSize;
}
