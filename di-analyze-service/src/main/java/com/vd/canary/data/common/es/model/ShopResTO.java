package com.vd.canary.data.common.es.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopResTO implements Serializable {

    private String skuID;
    //商品名称
    private String skuName;
    //商品图片地址
    private String skupic;
    //商品价格
    private String skuprice;
    //商品标题
    private String skuTitle;
    //商品副标题
    private String skuSubtitle;
    //商品计价单位
    private String unit;
}
