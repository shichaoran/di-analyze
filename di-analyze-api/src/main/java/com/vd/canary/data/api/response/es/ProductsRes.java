package com.vd.canary.data.api.response.es;

import com.alibaba.fastjson.JSON;
import lombok.*;
import lombok.experimental.Accessors;

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
public class ProductsRes implements Serializable {



    //前台一级分类id
    private String fOneCategoryId;
    //前台一级分类code
    private String fOneCategoryCode;
    //前台一级分类
    private String fOneCategoryName;
    //前台二级分类id
    private String fTwoCategoryId;
    //前台二级分类code
    private String fTwoCategoryCode;
    //前台二级分类
    private String fTwoCategoryName;
    //前台三级分类id
    private String fThreeCategoryId;
    //前台三级分类code
    private String fThreeCategoryCode;
    //前台三级分类
    private String fThreeCategoryName;
    //品牌id
    private String bBrandId;
    //品牌名称
    private String bBrandName;
    //skuid
    private String skuId;
    //sku标题
    private String proSkuTitle;
    //sku副标题
    private String proSkuSubTitle;
    //sku图片地址
    private String proSkuSkuPic;
    //商品属性 key为属性id+属性类型，value为属性值
    private Map<String, HashSet> attributeMap;
    //商品定价
    private JSON skuSellPriceJson;
    //定价类型
    private String skuSellPriceType;
    //sku创建时间
    private Date skuGmtCreateTime;

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
