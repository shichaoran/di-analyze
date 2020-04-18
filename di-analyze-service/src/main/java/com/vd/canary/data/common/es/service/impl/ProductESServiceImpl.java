package com.vd.canary.data.common.es.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.vd.canary.data.api.request.es.CategoryReq;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.request.es.ThreeCategoryReq;
import com.vd.canary.data.common.es.helper.ESPageRes;
import com.vd.canary.data.common.es.helper.ElasticsearchUtil;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.ProductESService;
import com.vd.canary.data.constants.Constant;
import com.vd.canary.data.util.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.springframework.stereotype.Service;

/**
 * 商品 ES 业务逻辑实现类
 */
@Slf4j
@Data
@Service
public class ProductESServiceImpl implements ProductESService {

    // 索引
    private String indexName = "productindex";

    //类型
    private String esType = "producttype";

    // 创建索引
    public String createIndex() {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            if (ElasticsearchUtil.createIndex(indexName, createIndexMapping(indexName))) {
                return "Create productindex success.";
            } else {
                return "Create productindex failure！";
            }
        } else {
            return "Productindex exist！";
        }
    }

    //新增商品信息
    public String saveProduct(ProductsTO product) throws IOException {
        if (product == null || StringUtils.isEmpty(product.getSkuId())) {
            return "param is null.";
        }
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(product).toString());
        String id = ElasticsearchUtil.addData(jsonObject, indexName, product.getSkuId());
        if (StringUtils.isNotBlank(id)) {
            return "SaveProduct success.";
        } else {
            return "SaveProduct failure!";
        }
    }
    //新增商品信息
    public String saveProduct(JSONObject product) throws IOException {
        if (product == null || StringUtils.isEmpty(product.get("skuId").toString())) {
            return "param is null.";
        }
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        JSONObject jsonObject = JSONObject.parseObject(product.toString());
        String id = ElasticsearchUtil.addData(jsonObject, indexName, product.get("skuId").toString());
        if (StringUtils.isNotBlank(id)) {
            return "SaveProduct success.";
        } else {
            return "SaveProduct failure!";
        }
    }
    public String saveProduct(String product,String productId) throws IOException {
        if (productId == null ) {
            return "param is null.";
        }
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        System.out.println(product);
        JSONObject jsonObject = JSONObject.parseObject(product);
        String id = ElasticsearchUtil.addData(jsonObject, indexName, productId);
        if (StringUtils.isNotBlank(id)) {
            return "SaveProduct success.";
        } else {
            return "SaveProduct failure!";
        }
    }

    //新增或修改商品信息
    public void saveOrUpdateProduct(ProductsTO product) throws IOException {
        if (product == null || StringUtils.isEmpty(product.getSkuId())) {
            return;
        }
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        if (ElasticsearchUtil.existById(indexName, product.getSkuId())) {
            Map content = JSONObject.parseObject(JSONObject.toJSONString(product), Map.class);
            ElasticsearchUtil.updateData(content, indexName, product.getSkuId());
            log.info("indexName:{},skuid:{},update product .", indexName, product.getSkuId());
        } else {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(product).toString());
            String id = ElasticsearchUtil.addData(jsonObject, indexName, product.getSkuId());
            log.info("indexName:{},skuid:{},save product return id: {}", indexName, product.getSkuId(), id);
        }
    }

    // 批量新增商品信息
    public void batchAddProduct(List<ProductsTO> products) {
        if (CollectionUtils.isEmpty(products)) {
            return;
        }
        if (products == null || products.size() == 0) {
            return;
        }
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        Map<String, JSONObject> map = new HashMap<>();
        for (ProductsTO product : products) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(product).toString());
            map.put(product.getSkuId(), jsonObject);
        }
        ElasticsearchUtil.insertBatch(indexName, map);
    }

    //删除商品信息
    public void deletedProductById(String id) throws IOException {
        ElasticsearchUtil.deleteById(indexName, id);
    }

    // 根据productId更新信息
    public void updateProduct(ProductsTO product) throws IOException {
        saveOrUpdateProduct(product);
    }
    public void updateProduct(Map<String,Object> map) throws IOException {
        ElasticsearchUtil.updateData(map, indexName, map.get("skuId").toString());
        log.info("indexName:{},skuid:{},update product,map{} .", indexName, map.get("skuId").toString(),map);
    }

    // 通过id获取数据
    public Map<String, Object> findById(String id) throws IOException {
        return ElasticsearchUtil.searchDataById(indexName, id);
    }

    // 通过 skuid 数组列表返回查询结果，不分页
    public List<Map<String, Object>> findByIds(CategoryReq categoryReq) {
        List<Map<String, Object>> result = Lists.newArrayList();
        if(categoryReq == null || categoryReq.getSkuIdList().size() == 0){
            return result;
        }
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termsQuery("skuId", categoryReq.getSkuIdList()));
        List<Map<String, Object>> list = ElasticsearchUtil.searchByQuery(indexName,boolQuery);
        return list;
    }

//    public List<Map<String, Object>> findByIds(List<String> skuIdList) {
//        List<Map<String, Object>> result = Lists.newArrayList();
//        if(skuIdList == null || skuIdList.size() == 0){
//            return result;
//        }
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.must(QueryBuilders.termsQuery("skuId", skuIdList));
//        List<Map<String, Object>> list = ElasticsearchUtil.searchByQuery(indexName,boolQuery);
//        return list;
//    }

    //通过一级类目 二级类目 三级类目 分页搜索数据 分页
    public ESPageRes boolQueryByDiffCategorys(Integer pageNumber, Integer pageSize, @Valid ThreeCategoryReq req) {
        if (req == null || ( req.getFOneCategoryId()==null && req.getFTwoCategoryId()==null && req.getFThreeCategoryId()== null ) ) {
            List<Map<String, Object>> recordList = new ArrayList<>();
            return new ESPageRes(pageNumber, pageSize, 0, recordList);
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
        if( req.getFOneCategoryId() != null){
            boolQuery.must(QueryBuilders.termQuery("fOneCategoryId", req.getFOneCategoryId() ));
        }
        if( req.getFTwoCategoryId() != null){
            boolQuery.must(QueryBuilders.termQuery("fTwoCategoryId", req.getFTwoCategoryId() ));
        }
        if( req.getFThreeCategoryId() != null){
            boolQuery.must(QueryBuilders.termQuery("fThreeCategoryId", req.getFThreeCategoryId() ));
        }
        ESPageRes esPageRes = ElasticsearchUtil.searchDataPage(indexName, pageNumber, pageSize, boolQuery, fields, sortField, sortTpye, highlightField);
        return esPageRes;
    }


    /**
     * 功能：首页顶部商品搜索框通过 关键字分词查询  支持 高亮 排序 并分页
     * 使用QueryBuilders
     * .termQuery("key", obj) 完全匹配
     * .termsQuery("key", obj1, obj2..)   一次匹配多个值
     * .matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
     * .multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
     * .matchAllQuery();         匹配所有文件
     * .termQuery(key+".keyword",value) 精准查找
     * 组合查询 QueryBuilders.boolQuery()
     * .must(QueryBuilders) :   AND
     * .mustNot(QueryBuilders): NOT
     * .should:                  : OR
     */
    public ESPageRes boolQueryByKeyword(Integer pageNumber, Integer pageSize, ProductsReq req) {
        if (req == null) {
            List<Map<String, Object>> recordList = new ArrayList<>();
            return new ESPageRes(0, 0, 0, recordList);
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
        if (StringUtils.isNotBlank(req.getKey())) {// keyword 关键字搜索
            String escapeKey = QueryParser.escape(req.getKey());
            boolQuery.must(QueryBuilders.multiMatchQuery(escapeKey,
                    "proSkuSpuName", "proSkuSkuName", "proSkuTitle", "proSkuSubTitle",
                    "threeCategoryName", "bBrandName", "brandShorthand").fuzziness(Fuzziness.AUTO));
        }
        if (req.getBBrandId() != null && req.getBBrandId().size() > 0) {//品牌id
            boolQuery.must(QueryBuilders.termsQuery("proSkuBrandId", req.getBBrandId()));
        }
        if (req.getFThreeCategoryName() != null && req.getFThreeCategoryName().size() > 0) {//后台三级分类id
            boolQuery.must(QueryBuilders.termsQuery("fThreeCategoryId", req.getFThreeCategoryName()));
        }
        if (req.getBusinessArea() != null && req.getBusinessArea().size() > 0) { //供货区域id
            boolQuery.must(QueryBuilders.termsQuery("regionalId", req.getBusinessArea()));
        }
        if (StringUtils.isNotBlank(req.getPriceSort())) {
            sortField = "skuSellPriceJson"; // 商品定价信息，需要嵌套查询xxx.xxx
            sortTpye = req.getPriceSort(); // 商品价格排序
        }
        if (StringUtils.isNotBlank(req.getIsDiscussPrice())) {//是否议价，需要嵌套查询xxx.xxx
            //boolQuery.must(QueryBuilders.rangeQuery("skuSellPriceJson").from(30).to(60).includeLower(true).includeUpper(true)); //适用价格区间查找
            boolQuery.must(QueryBuilders.rangeQuery("skuSellPriceJson").gt(0));
            //boolQuery.mustNot();
        }
        if (StringUtils.isNotBlank(req.getIsHaveHouse())) {//是否入驻展厅
            //boolQuery.must();
        }
        ESPageRes esPageRes = ElasticsearchUtil.searchDataPage(indexName, pageNumber, pageSize, boolQuery, fields, sortField, sortTpye, highlightField);
        return esPageRes;
    }

    /**
     * index mapping
     * 说明：xx.startObject("m_id").field("type","keyword").endObject().field("type", "date")
     * .field("format", "yyyy-MM")  //m_id:字段名,type:文本类型,analyzer 分词器类型
     * 该字段添加的内容，查询时将会使用ik_max_word 分词 //ik_smart  ik_max_word  standard
     * 创建索引有三种方式：1、HTTP的方式创建的列子；2、Map创建的方式；3、使用Builder的方式；
     */
    private XContentBuilder createIndexMapping(String indexName){
        // 方式三：使用XContentBuilder
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject(indexName+"_type");
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("skuId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("proSkuBrandId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("proSkuSpuId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("proSkuSpuCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("proSkuSpuName"); { builder.field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart"); }
                        builder.endObject();
                        builder.startObject("proSkuSkuCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("proSkuSkuName"); { builder.field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart"); }
                        builder.endObject();
                        builder.startObject("proSkuTitle"); { builder.field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart"); }
                        builder.endObject();
                        builder.startObject("proSkuSubTitle"); { builder.field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart"); }
                        builder.endObject();
                        builder.startObject("threeCategoryId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("threeCategoryCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("threeCategoryName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("skuSupplierId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("skuSupplierName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("skuState"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("proSkuSkuPicJson"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("skuValuationUnit"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("skuIntroduce"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("skuAuxiliaryUnit"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("skuGmtCreateTime"); { builder.field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss"); }
                        builder.endObject();
                        builder.startObject("skuGmtModifyTime"); { builder.field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss"); }
                        builder.endObject();
                        builder.startObject("spuState"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("proSpuSpuPic"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("spuTitle"); { builder.field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart"); }
                        builder.endObject();
                        builder.startObject("attributeCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("attributeName"); { builder.field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart"); }
                        builder.endObject();
                        builder.startObject("value_Name"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("attributeId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("attributeValueId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("attributeMap"); { builder.field("type", "object"); }
                        builder.endObject();
                        builder.startObject("attributeType"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("oneCategoryId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("oneCategoryCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("oneCategoryName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("twoCategoryId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("twoCategoryCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("twoCategoryName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("brandCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("bBrandName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("brandLoge"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("brandShorthand"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("brandIntroduction"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fOneCategoryId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fOneCategoryCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fOneCategoryName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fTwoCategoryId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fTwoCategoryCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fTwoCategoryName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fThreeCategoryId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fThreeCategoryCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fThreeCategoryName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("type"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fileUrl"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("fileSortNumber"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("regionalCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("regionalName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("regionalScope"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("skuSellPriceJson"); { builder.field("type", "nested"); }
                        builder.endObject();
                        builder.startObject("skuSellPriceType"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("warehouseId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("warehouseName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("inventory"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("regionalId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("skuRegionalName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("storeId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("categoryId"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("storeName"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("businessCategory"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("mainProducts"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("businessArea"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("boothBusinessBoothCode"); { builder.field("type", "nested"); }
                        builder.endObject();
                        builder.startObject("customerProfilesLevel"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("approveState"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("enterpriseType"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("storeInfoStoreQrCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("gmtCreateTime"); { builder.field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss"); }
                        builder.endObject();
                        builder.startObject("boothScheduledTime"); { builder.field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss"); }
                        builder.endObject();
                        builder.startObject("warehouseCode"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("warehouseType"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("warehouseRegional"); { builder.field("type", "keyword"); }
                        builder.endObject();
                        builder.startObject("detailedAddress"); { builder.field("type", "keyword"); }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder;
    }


    // start 测试索引productindex1 专用
    public String testAddData(String c) throws IOException {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        Map<String,Object> map = new HashMap();
        map.put("id","00b6d5612f734414d68088e359e4b008");// 主键id
        map.put("user_name", "zhangsan");// 用户名
        map.put("user_password", "123456");// 密码
        map.put("user_real_name", "张三");// 用户真实姓名
        //map.put("create_time", DateUtil.getCurrentTimeStr());// 创建时间
        map.put("is_deleted", 0);// 是否删除
        JSONObject jsonObject= JSONObject.parseObject(JSON.toJSONString(map));
        String id = ElasticsearchUtil.addData(jsonObject, indexName, "00b6d5612f734414d68088e359e4b008");
        if (StringUtils.isNotBlank(id)) {
            return "SaveProduct success.";
        } else {
            return "SaveProduct failure!";
        }
    }
    public String insertBatch(String c) throws IOException {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        Map<String ,JSONObject> mapJSON = new HashMap<>();
        Map<String,Object> map = new HashMap();
        map.put("id","00b6d5612f734414d68088e359e4b009");// 主键id
        map.put("user_name", "lisi");// 用户名
        map.put("user_password", "123457");// 密码
        map.put("user_real_name", "李四");// 用户真实姓名
        map.put("create_time", DateUtil.getCurrentTimeStr());// 创建时间
        map.put("is_deleted", 0);// 是否删除
        JSONObject jsonObject= JSONObject.parseObject(JSON.toJSONString(map));
        mapJSON.put("00b6d5612f734414d68088e359e4b009",jsonObject);
        Map<String,Object> map1 = new HashMap();
        map1.put("id","00b6d5612f734414d68088e359e4b010");// 主键id
        map1.put("user_name", "wangwu");// 用户名
        map1.put("user_password", "123458");// 密码
        map1.put("user_real_name", "王五");// 用户真实姓名
        map1.put("create_time", DateUtil.getCurrentTimeStr());// 创建时间
        map1.put("is_deleted", 1);// 是否删除
        JSONObject jsonObject1= JSONObject.parseObject(JSON.toJSONString(map1));
        mapJSON.put("00b6d5612f734414d68088e359e4b010",jsonObject1);
        ElasticsearchUtil.insertBatch(indexName,mapJSON);
        return null;
    }
    public String updateOne(String c) throws IOException {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        Map<String, Object> content = new HashMap();
        content.put("user_real_name", "张三1");
        content.put("create_time", DateUtil.getCurrentTimeStr());// 创建时间
        String id = ElasticsearchUtil.updateData(content, indexName, "00b6d5612f734414d68088e359e4b008");
        if (StringUtils.isNotBlank(id)) {
            return "SaveProduct success.";
        } else {
            return "SaveProduct failure!";
        }
    }
    public String deleteOne(String c) throws IOException {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        ElasticsearchUtil.deleteById( indexName, "00b6d5612f734414d68088e359e4b008");
        return  "Succ";
    }

    // end 测试索引productindex1 专用
    public String testAddProData(String c) throws IOException {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        Map<String,Object> map = new HashMap();
        map.put("skuId","1");// 主键id
        map.put("proSkuBrandId", "1");
        map.put("proSkuSpuId", "10");
        map.put("proSkuSpuCode", "123");
        map.put("proSkuSpuName", "钢");
        map.put("proSkuSkuCode", "12fdfv3");
        map.put("proSkuSkuName", "钢贸");
        map.put("proSkuTitle", "标题");
        map.put("proSkuSubTitle", "副标题");
        map.put("threeCategoryId", "123");
        map.put("threeCategoryCode", "123");
        map.put("threeCategoryName", "铜矿");
        map.put("skuSupplierId", "123");
        map.put("skuSupplierName", "123");
        map.put("skuState", "123");
        map.put("proSkuSkuPicJson", "123");
        map.put("skuValuationUnit", "123");
        map.put("skuIntroduce", "123");
        map.put("skuAuxiliaryUnit", "123");
        map.put("skuGmtCreateTime", "123");
        map.put("skuGmtModifyTime", "123");
        map.put("spuState", "123");
        map.put("proSpuSpuPic", "123");
        map.put("spuTitle", "123");
        map.put("attributeCode", "123");
        map.put("attributeName", "123");
        map.put("value_Name", "123");
        map.put("attributeId", "123");
        map.put("attributeValueId", "123");
        map.put("attributeMap", "123");
        map.put("attributeType", "123");
        map.put("oneCategoryId", "123");
        map.put("oneCategoryCode", "123");
        map.put("oneCategoryName", "123");
        map.put("twoCategoryId", "123");
        map.put("twoCategoryCode", "123");
        map.put("twoCategoryName", "123");
        map.put("brandCode", "123");
        map.put("bBrandName", "123");
        map.put("brandLoge", "123");
        map.put("brandShorthand", "123");
        map.put("brandIntroduction", "123");
        map.put("fOneCategoryId", "123");
        map.put("fOneCategoryCode", "123");
        map.put("fOneCategoryName", "装配式建筑");
        map.put("fTwoCategoryId", "123");
        map.put("fTwoCategoryCode", "123");
        map.put("fTwoCategoryName", "金属矿");
        map.put("fThreeCategoryId", "123");
        map.put("fThreeCategoryCode", "123");
        map.put("fThreeCategoryName", "123");
        map.put("type", "123");
        map.put("fileUrl", "123");
        map.put("fileSortNumber", "123");
        map.put("regionalCode", "123");
        map.put("regionalName", "123");
        map.put("regionalScope", "123");
        map.put("skuSellPriceJson", "123");
        map.put("skuSellPriceType", "123");
        map.put("warehouseId", "123");
        map.put("warehouseName", "123");
        map.put("inventory", "123");
        map.put("regionalId", "123");
        map.put("skuRegionalName", "123");
        map.put("storeId", "123");
        map.put("categoryId", "123");
        map.put("storeName", "123");
        map.put("businessCategory", "123");
        map.put("mainProducts", "123");
        map.put("businessArea", "123");
        map.put("boothBusinessBoothCode", "123");
        map.put("customerProfilesLevel", "123");
        map.put("approveState", "123");
        map.put("enterpriseType", "123");
        map.put("storeInfoStoreQrCode", "123");
        map.put("gmtCreateTime", "123");
        map.put("boothScheduledTime", "123");
        map.put("warehouseCode", "123");
        map.put("warehouseType", "123");
        map.put("warehouseRegional", "123");
        map.put("detailedAddress", "123");


        JSONObject jsonObject= JSONObject.parseObject(JSON.toJSONString(map));
        String id = ElasticsearchUtil.addData(jsonObject, indexName, "1234");
        if (StringUtils.isNotBlank(id)) {
            return "SaveProduct success.";
        } else {
            return "SaveProduct failure!";
        }
    }


}