package com.vd.canary.data.api.response.es;

import com.alibaba.fastjson.JSON;
import com.vd.canary.data.api.response.es.vo.ProductsDetailRes;
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

    private Map<String,String> categorys;//fThreeCategoryId:fThreeCategoryName

    private Map<String,String> brands; //proSkuBrandId:bBrandName

    private Map<String,Map<String,String>> attributes; //属性

    private List<ProductsDetailRes> productDetailRes; //商品详细列表

    private Integer total;//总条数


}
