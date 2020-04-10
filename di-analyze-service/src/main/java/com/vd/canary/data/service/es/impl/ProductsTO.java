package com.vd.canary.data.service.es.impl;

import com.alibaba.fastjson.JSON;
import lombok.*;
import lombok.experimental.Accessors;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author WangRuilin
 * @Date 2020/4/9 17:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ProductsTO implements Serializable {

    /**
     * product_sku表   商品SKU
     */
    //skuId
    private String skuId;
    //品牌id
    private String proSkuBrandId;
    //spuid
    private String proSkuSpuId;
    //spu编码
    private String proSkuSpuCode;
    //spu名称
    private String proSkuSpuName;
    //sku编码
    private String proSkuSkuCode;
    //sku名称
    private String proSkuSkuName;
    //sku标题
    private String proSkuTitle;
    //sku副标题
    private String proSkuSubTitle;
    //后台三级分类id
    private String threeCategoryId;
    //后台三级分类code
    private String threeCategoryCode;
    //后台三级分类
    private String threeCategoryName;
    //供应商id
    private String skuSupplierId;
    //供应商名称
    private String skuSupplierName;
    //sku状态
    private String skuState;
    //sku图片地址
    private String proSkuSkuPic;
    //计量单位
    private String skuValuationUnit;
    //sku介绍
    private String  skuIntroduce;
    /**
     * 属性attribute_aggregation
     */
    //sku创建时间
    private Date skuGmtCreateTime;
    //sku修改时间
    private Date skuGmtModifyTime;


    /**
     * product_spu表  商品spu
     */
    //spu状态
    private String spuState;
    //spu图片地址
    private String proSpuSpuPic;
    //spu广告词/副标题
    private String spuTitle;


    /**
     * attribute_management表   属性管理
     */
    //属性编码
    private String attributeCode;
    //属性名称
    private String attributeName;


    /**
     * attribute_value表   属性值
     */
    //属性值名称
    private String value_Name;


    /**
     * attribute_value_relations表   属性值关系表
     */
    //属性id
    private String attributeId;
    //属性值id
    private String attributeValueId;
    /**
     * 商品属性 key为属性id+属性类型，value为属性值
     */
    private Map<String, HashSet> attributeMap;


    /**
     * sku_attribute_relations表   sku属性映射关系
     */
    //属性类型：0定价属性   1一般属性
    private String attriType;


    /**
     * background_category表  后台类目
     */
    //后台一级分类id
    private String oneCategoryId;
    //后台一级分类code
    private String oneCategoryCode;
    //后台一级分类
    private String oneCategoryName;
    //后台二级分类id
    private String twoCategoryId;
    //后台二级分类code
    private String twoCategoryCode;
    //后台二级分类
    private String twoCategoryName;

    /**
     * brand_management表   品牌管理
     */
    //品牌编码
    private String brandCode;
    //品牌名称
    private String bBrandName;
    //品牌Logo
    private String brandLoge;
    //品牌简写
    private String brandShorthand;
    //品牌介绍
    private String brandIntroduction;


    /**
     * category_attribute_relations表   类目属性映射关系
     */


    /**
     * category_relations前后台类目映射关系
     */
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

    /**
     * file_management表   文件管理
     */
    //文件类型  0图片 1视频
    private String type;
    //文件存储地址
    private String fileUrl;
    //排序号
    private String fileSortNumber;


    /**
     * regional_management表   区域管理
     */
    //区域编码
    private String regionalCode;
    //区域名称
    private String regionalName;
    //区域范围json
    private Text regionalScope;

    /**
     * sku_selling_price表   sku定价管理
     */
    //商品定价信息
    private JSON skuSellPriceJson;
    //定价类型
    private String skuSellPriceType;


    /**
     * sku_warehouse_relations表   sku仓库关联表
     */
    //仓库id
    private String warehouseId;
    //仓库名称
    private String warehouseName;
    //库存
    private String inventory;
    //供货区域id
    private String regionalId;
    //供货区域名称
    private String skuRegionalName;


    /**
     * store_product_relations   店铺商品
     */
    //店铺id
    private String storeId;
    //店铺分类id
    private String categoryId;

    /**
    * warehouse_management表    仓库管理
    */
    //仓库编码
    private String warehouseCode;
    //仓库类型
    private String warehouseType;
    //省市区json
    private String warehouseRegional;
    //详细地址
    private String detailedAddress;


    /**
     * 附加字段
     */
    // price为desc 或者 asc
    private String priceSort;
    //是否议价
    private String isDiscussPrice;
    //是否入驻
    private String isHaveHouse;


}
