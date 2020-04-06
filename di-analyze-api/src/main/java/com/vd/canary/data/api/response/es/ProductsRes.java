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
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class ProductsRes implements Serializable {

    //前台一级分类id
    private Integer f_one_category_id;
    //前台一级分类code
    private String f_one_category_code;
    //前台一级分类
    private String f_one_category_name;
    //前台二级分类id
    private Integer f_two_category_id;
    //前台二级分类code
    private String f_two_category_code;
    //前台二级分类
    private String f_two_category_name;
    //前台三级分类id
    private Integer f_three_category_id;
    //前台三级分类code
    private String f_three_category_code;
    //前台三级分类
    private String f_three_category_name;
    //品牌名称
    private String b_brand_name;
    //sku标题
    private String pro_sku_title;
    //sku副标题
    private String pro_sku_sub_title;
    //sku图片地址
    private String pro_sku_sku_pic;
    //属性列表
    private Map<String, HashSet> attribute_map;
    //商品定价
    private JSON sku_sell_price_json;
    //定价类型
    private Integer sku_sell_price_type;
    //sku创建时间
    private Date sku_gmt_create_time;

    //店铺名称
    private String store_info_name;
    //主营类目
    private String business_category;
    //主营产品
    private String main_products;
    //所在地区
    private String business_area;
    //展厅编号
    private String booth_business_booth_code;
    //会员等级
    private String customer_profiles_level;
    //认证信息
    private String approve_state;
    //供方类别
    private String enterprise_type;
    //店铺二维码
    private String store_info_store_qr_code;
    //创建时间
    private Date gmt_create_time;


}
