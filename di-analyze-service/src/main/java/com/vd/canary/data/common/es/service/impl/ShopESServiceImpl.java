package com.vd.canary.data.common.es.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.vd.canary.data.common.es.helper.ElasticsearchUtil;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.util.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 店铺 ES 业务逻辑实现类
 *
 */
@Slf4j
@Data
@Service
public class ShopESServiceImpl {

    // 索引
    private String indexName="shopindex";

    //类型
    private String esType="shoptype";

    // 创建索引
    public String createIndex() {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            if (ElasticsearchUtil.createIndex(indexName,createIndexMapping() )) {//index json file!!!!
                return "create shopindex success.";
            } else {
                return "create shopindex failure！";
            }
        } else {
            return "shopindex exist！";
        }
    }

    //新增店铺信息
    public String saveShop(ShopTO shop) throws IOException {
        if(shop == null || StringUtils.isEmpty(shop.getId()) ){
            return "param is null.";
        }
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName,createIndexMapping());
        }
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(shop).toString());
        String id = ElasticsearchUtil.addData(jsonObject,indexName,shop.getId());
        if(StringUtils.isNotBlank(id)){
            return "Save Shop success.";
        }
        else{
            return "Save Shop failure!";
        }
    }

    //新增或修改店铺信息
    public void saveOrUpdateShop(ShopTO shop) throws IOException {
        if(shop == null || StringUtils.isEmpty(shop.getId()) ){
            return ;
        }
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName,createIndexMapping());
        }
        if(ElasticsearchUtil.existById(indexName,shop.getId())){
            Map content = JSONObject.parseObject(JSONObject.toJSONString(shop), Map.class);
            ElasticsearchUtil.updateData(content,indexName,shop.getId());
            log.info("indexName:{},skuid:{},update shop .",indexName, shop.getId());
        }else{
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(shop).toString());
            String id = ElasticsearchUtil.addData(jsonObject,indexName,shop.getId());
            log.info("indexName:{},skuid:{},save shop return id: {}",indexName, shop.getId(),id);
        }
    }

    // 批量新增商品信息
    public void batchAddShop(List<ShopTO> shops) {
        if(CollectionUtils.isEmpty(shops)) {
            return ;
        }
        if(shops == null || shops.size() == 0 ){
            return ;
        }
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName,createIndexMapping());
        }
        Map<String ,JSONObject> map = new HashMap<>();
        for(ShopTO shop :shops) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(shop).toString());
            map.put(shop.getId(),jsonObject);
        }
        ElasticsearchUtil.insertBatch(indexName,map);
    }

    //删除店铺信息
    public void deletedShopById(String id) throws IOException {
        ElasticsearchUtil.deleteById(indexName,id);
    }

    // 根据Id更新店铺信息
    public void updateShop(ShopTO shop) throws IOException {
        saveOrUpdateShop(shop);
    }

    //通过id获取数据
    public ShopTO findById(String id) throws IOException{
        return (ShopTO)ElasticsearchUtil.searchDataById(indexName,id);
    }




    /**
     * index mapping
     * 说明：xx.startObject("m_id").field("type","keyword").endObject()  //m_id:字段名,type:文本类型,analyzer 分词器类型
     *      该字段添加的内容，查询时将会使用ik_max_word 分词 //ik_smart  ik_max_word  standard
     *      创建索引有三种方式：1、HTTP的方式创建的列子；2、Map创建的方式；3、使用Builder的方式；
     */
    private XContentBuilder createIndexMapping(){
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder().startObject().startObject("properties")
                    //.startObject("m_id").field("type","keyword").endObject()  //m_id:字段名,type:文本类型,analyzer 分词器类型
                    //
                    .startObject("id").field("type", "text").endObject()
                    .startObject("pdfId").field("type", "text").endObject()
                    .startObject("title").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .startObject("author").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .startObject("content").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .startObject("columnName").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .startObject("articlesSource").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .startObject("periodicalDate").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    // .field("type", "date").field("format", "yyyy-MM")
                    .endObject().startObject("settings").field("number_of_shards", 3).field("number_of_replicas", 1)
                    .endObject().endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapping;
    }


}