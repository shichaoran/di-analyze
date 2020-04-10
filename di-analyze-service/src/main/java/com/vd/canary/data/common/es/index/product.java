package com.vd.canary.data.common.es.index;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "productindex", type = "product",shards = 5, replicas = 2)
public class product implements Serializable {

    @Id
    private Long productId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String productName;

}
