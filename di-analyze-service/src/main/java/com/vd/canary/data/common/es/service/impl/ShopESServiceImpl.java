package com.vd.canary.data.common.es.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.vd.canary.data.common.es.helper.ElasticsearchUtil;
import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.util.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.lang3.StringUtils;
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
            if (ElasticsearchUtil.createIndex(indexName)) {//index json file!!!!
                return "create shopindex success.";
            } else {
                return "create shopindex failure！";
            }
        } else {
            return "shopindex exist！";
        }
    }

    //新增店铺信息
    @Deprecated
    public String saveShop(ShopTO shop) throws IOException {
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
    public void saveOrUpdateProduct(ShopTO shop) throws IOException {
        if(shop == null || StringUtils.isEmpty(shop.getId()) ){
            return ;
        }
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName);
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

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @Autowired
//    private ShopTORepository shopRepository;
//
//    //新增店铺信息
//    public void saveProduct(ShopTO shop) {
//        shopRepository.save(shop);
//    }
//
//    // 批量新增店铺信息
//    public void batchAddShop(List<ShopTO> shops) {
//        if(CollectionUtils.isEmpty(shops)) {
//            return ;
//        }
//        List<IndexQuery> queries = Lists.newArrayListWithExpectedSize(shops.size());
//        IndexQuery indexItem  = null;
//        for(ShopTO shop :shops) {
//            indexItem = new IndexQuery();
//            indexItem.setObject(shop);
//            queries.add(indexItem);
//        }
//        elasticsearchTemplate.bulkIndex(queries);
//    }
//
//    //删除店铺信息
//    public void deletedShopById(String id) {
//        shopRepository.deleteById(id);
//    }
//
//    // 根据Id更新店铺信息
//    public void updateShop(ShopTO shop) {
//        UpdateQuery updateQuery = new UpdateQuery();
//        updateQuery.setId(shop.getId().toString());
//        updateQuery.setClazz(ShopTO.class);
//        shop.setId(null);
//        UpdateRequest request = new UpdateRequest();
//        try{
//            request.doc(JsonUtils.beanToJson(shop));
//        }catch (Exception e){
//        }
//        updateQuery.setUpdateRequest(request);
//        elasticsearchTemplate.update(updateQuery);
//    }
//
//    public ShopTO findById(String id) {
//        Optional<ShopTO> shopOptional= shopRepository.findById(id);
//        return shopOptional.get();
//    }

}