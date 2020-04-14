package com.vd.canary.data.api.response.es;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.management.relation.Role;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.List;
import java.util.Map;

/**
 * @Author shichaoran
 * @Date 2020/4/9 20:33
 * @Version
 */
@Data
@ToString
@Accessors(chain = true)
public class ShopProductRes implements Serializable {
    /**
     * 商品Id
     */
    private String skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品图片地址
     */
    private String skuPic;
    /**
     * 商品价格
     */
    private String skuPrice;
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
    /**
     * 价格类型
     */
    private String priceType;
}