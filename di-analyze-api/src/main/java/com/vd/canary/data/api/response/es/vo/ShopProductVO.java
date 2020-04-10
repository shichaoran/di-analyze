package com.vd.canary.data.api.response.es.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author shichaoran
 * @Date 2020/4/10 11:27
 * @Version
 */
@Setter
@Getter
public class ShopProductVO implements Serializable {
    /**
     * 商品Id
     */
    private String skuID;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品图片地址
     */
    private String skupic;
    /**
     * 商品价格
     */
    private String skuprice;
    /**
     * 商品标题
     */
    private String skuTitle;
    /**
     * 商品副标题
     */
    private String skuSubtitle;
    /**
     * 商品计价单位
     */
    private String unit;

}
