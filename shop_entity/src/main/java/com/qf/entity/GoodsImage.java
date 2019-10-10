package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("goods_images")
public class GoodsImage extends BaseEntity {

    private Integer gid;
    private String info;
    private String url;
    private Integer isfengmian = 0;//0 - 非封面 1 - 封面
}
