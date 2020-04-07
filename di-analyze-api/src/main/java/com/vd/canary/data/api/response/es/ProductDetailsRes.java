package com.vd.canary.data.api.response.es;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class ProductDetailsRes implements Serializable {
    //sku标题
    private String skuTitle;
    //sku副标题
    private String skuSubTitle;
    //商品属性
    private Map<String, HashSet> attributeMap;
    //商品价格
    private JSON priceJson;
    //价格类型
    private Integer priceType;
    //sku描述
    private Integer skuIntroduce;
    //sku图片地址
    private String proSkuSkuPic;

    //店铺名称
    private String storeInfoName;
    //主营类目
    private String businessCategory;
    //主营产品
    private String mainProducts;
    //所在地区
    private String businessArea;
    //展厅编号
    private List boothBusinessBoothCode;
    //会员等级
    private String customerProfilesLevel;
    //认证信息
    private String approveState;
    //供方类别
    private String enterpriseType;
    //店铺二维码
    private String storeInfoStoreQrCode;
    //创建时间
    private Date gmtCreateTime;
}
