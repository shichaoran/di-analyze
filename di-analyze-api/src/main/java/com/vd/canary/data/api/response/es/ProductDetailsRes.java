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
public class ProductDetailsRes implements Serializable {
    //sku标题
    private String sku_title;
    //sku副标题
    private String sku_sub_title;
    //商品属性
    private Map<String, HashSet> attribute_map;
    //商品价格
    private JSON price_json;
    //价格类型
    private Integer price_type;
    //sku描述
    private Integer sku_introduce;
    //sku图片地址
    private String pro_sku_sku_pic;

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
