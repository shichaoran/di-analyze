package com.vd.canary.data.api.response.es.vo;

import lombok.*;
import lombok.experimental.Accessors;
import org.bouncycastle.util.StringList;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ProductsDetailRes implements Serializable {

    //skuid
    private String skuId;
    //skuname
    private String skuName;
    //sku标题
    private String proSkuTitle;
    //sku副标题
    private String proSkuSubTitle;
    //sku图片地址
    private String proSkuSkuPic;
    //商品属性 key为属性id+属性类型，value为属性值
    private String attributeMap ;//Map<String, HashSet<String>> attributeMap;
    //商品定价
    private String skuSellPriceJson;
    //定价类型
    private Integer skuSellPriceType;
    //sku创建时间
    private String skuGmtCreateTime;
    //辅助单位
    private String skuAuxiliaryUnit;

    //店铺id
    private String shopId;
    //店铺名称
    private String storeInfoName;
    //主营类目
    private String businessCategory;
    //主营产品
    private String mainProducts;
    //所在地区
    private String businessArea;
    //展厅编号
    private String boothBusinessBoothCode; //List boothBusinessBoothCode;
    //会员等级
    private String customerProfilesLevel;
    //认证信息
    private String approveState;
    //供方类别
    private String enterpriseType;
    //店铺二维码
    private String storeInfoStoreQrCode;
    //创建时间
    private String gmtCreateTime;

    private Date boothScheduledTime; //入驻时间


}
