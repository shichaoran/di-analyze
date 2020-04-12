package com.vd.canary.data.common.es.index;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Getter
@Setter
@ToString
@Accessors(chain = true)
//@Document(indexName = "shopresindex", type = "shopres", shards = 5, replicas = 2)
public class ShopResTO implements Serializable {
    //@Id
    private String skuID;
    /**
     * 商品名称
     */
    //@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
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
    //@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
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
