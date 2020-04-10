package com.vd.canary.data.common.es.index;

import java.io.Serializable;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 所以实体类，
 * 支持类型：FieldType.text,FieldType.keyword,FieldType.Date,FieldType.Integer
 * 1对多在spring-data-elasticsearch 统一为nested类型，注解方法为：@Field(type = FieldType.Nested, includeInParent = true)
 *
 */
@Data
@Document(indexName = "productindex", type = "product", shards = 5, replicas = 2)
public class Product implements Serializable {

    @Id
    private Long productId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String productName;

}
