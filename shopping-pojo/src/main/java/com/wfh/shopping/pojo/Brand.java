package com.wfh.shopping.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("brand")
public class Brand {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    @TableField("first_char")
    private String firstChar;

    private String logo;

    @TableField("delete_flag")
    private Integer deleteFlag = 0;

    @TableField(exist = false)
    private Integer categoryId;
}
