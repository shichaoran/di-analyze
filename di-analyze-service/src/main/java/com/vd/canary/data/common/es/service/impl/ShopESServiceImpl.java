package com.vd.canary.data.common.es.service.impl;

import java.io.IOException;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.vd.canary.data.api.request.es.ShopPageReq;
import com.vd.canary.data.api.request.es.SearchShopReq;
import com.vd.canary.data.common.es.helper.ESPageRes;
import com.vd.canary.data.common.es.helper.ElasticsearchUtil;
import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.constants.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
     * 功能：首页顶部店铺搜索框通过 关键字分词查询  支持 高亮 排序 并分页
     *      使用QueryBuilders
     *          .termQuery("key", obj) 完全匹配
     *          .termsQuery("key", obj1, obj2..)   一次匹配多个值
     *          .matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
     *          .multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
     *          .matchAllQuery();         匹配所有文件
     *          .termQuery(key+".keyword",value) 精准查找
     *      组合查询 QueryBuilders.boolQuery()
     *          .must(QueryBuilders) :   AND
     *          .mustNot(QueryBuilders): NOT
     *          .should:                  : OR
     *
     */
    public ESPageRes boolQueryByKeyword(Integer pageNumber, Integer pageSize, ShopPageReq req) {
        if(req == null){
            List<Map<String, Object>> recordList = new ArrayList<>();
            return new ESPageRes(0, 0, 0, recordList );
        }
        if (pageNumber == null || pageNumber < Constant.ES_DEFAULT_PAGE_NUMBER) {
            pageNumber = Constant.ES_DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constant.ES_PAGE_SIZE;
        }
        String fields = null;
        String sortField = null;
        String sortTpye = null;
        String highlightField = null;
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(StringUtils.isNotBlank(req.getKey())){// keyword 关键字搜索
            String escapeKey = QueryParser.escape(req.getKey());
            boolQuery.must(QueryBuilders.multiMatchQuery(escapeKey,
                    "proSkuSpuName","proSkuSkuName","proSkuTitle","proSkuSubTitle",
                    "threeCategoryName","bBrandName","brandShorthand").fuzziness(Fuzziness.AUTO));
        }
        ESPageRes esPageRes = ElasticsearchUtil.searchDataPage(indexName,pageNumber,pageSize,boolQuery,fields,sortField,sortTpye,highlightField);
        return esPageRes;
    }

    // 通过关键字＋快速查找
    public ESPageRes boolQueryByKeyword(Integer pageNumber, Integer pageSize, SearchShopReq req) {
        if(req == null){
            List<Map<String, Object>> recordList = new ArrayList<>();
            return new ESPageRes(0, 0, 0, recordList );
        }
        if (pageNumber == null || pageNumber < Constant.ES_DEFAULT_PAGE_NUMBER) {
            pageNumber = Constant.ES_DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constant.ES_PAGE_SIZE;
        }
        String fields = null;
        String sortField = null;
        String sortTpye = null;
        String highlightField = null;
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(StringUtils.isNotBlank(req.getKey())){// keyword 关键字搜索
            String escapeKey = QueryParser.escape(req.getKey());
            boolQuery.must(QueryBuilders.multiMatchQuery(escapeKey,
                    "proSkuSpuName","proSkuSkuName","proSkuTitle","proSkuSubTitle",
                    "threeCategoryName","bBrandName","brandShorthand").fuzziness(Fuzziness.AUTO));
        }
        if(req.getBrandIds() != null && req.getBrandIds().size() > 0 ){//品牌id
            boolQuery.must(QueryBuilders.termsQuery("proSkuBrandId",req.getBrandIds()));
        }
        if(req.getCategoryIds() != null && req.getCategoryIds().size() > 0 ){//后台三级分类id
            boolQuery.must(QueryBuilders.termsQuery("businessCategory",req.getCategoryIds()));
        }
        if( req.isExhibitionJoined() ){//是否入驻展厅
            //boolQuery.must();
        }
        ESPageRes esPageRes = ElasticsearchUtil.searchDataPage(indexName,pageNumber,pageSize,boolQuery,fields,sortField,sortTpye,highlightField);
        return esPageRes;
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
                    .startObject("id").field("type", "keyword").endObject()
                    .startObject("name").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").endObject()
                    .startObject("boothCode").field("type", "keyword").endObject()
                    .startObject("mediaUrl").field("type", "keyword").endObject()
                    .startObject("businessCategory").field("type", "keyword").endObject()
                    .startObject("businessBrand").field("type", "keyword").endObject()
                    .startObject("businessArea").field("type", "keyword").endObject()
                    .startObject("imageOrder").field("type", "keyword").endObject()
                    .startObject("imageName").field("type", "keyword").endObject()
                    .startObject("imageUrl").field("type", "keyword").endObject()
                    .startObject("shopProductRes").field("type", "keyword").endObject()
                    .startObject("classify").field("type", "keyword").endObject()
                    .startObject("customerId").field("type", "keyword").endObject()
                    .startObject("storeTemplateId").field("type", "keyword").endObject()
                    .startObject("mainProducts").field("type", "keyword").endObject()
                    .startObject("boothScheduledTime").field("type", "date").field("format", "yyyy-MM-DD").endObject()
                    .startObject("level").field("type", "keyword").endObject()
                    .endObject().startObject("settings").field("number_of_shards", 3).field("number_of_replicas", 1)
                    .endObject().endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapping;
    }


}