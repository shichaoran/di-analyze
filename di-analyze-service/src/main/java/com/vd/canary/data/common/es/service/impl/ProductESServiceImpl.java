package com.vd.canary.data.common.es.service.impl;

import java.io.IOException;
import java.util.*;
import com.alibaba.fastjson.JSONObject;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.common.es.helper.ESPageRes;
import com.vd.canary.data.common.es.helper.ElasticsearchUtil;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.ProductESService;
import com.vd.canary.data.constants.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

/**
 * 商品 ES 业务逻辑实现类
 */
@Slf4j
@Data
@Service
public class ProductESServiceImpl implements ProductESService {

    // 索引
    private String indexName = "productindex1";

    //类型
    private String esType = "producttype1";

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

    // 通过id获取数据
    public ProductsTO findById(String id) throws IOException {
        return (ProductsTO) ElasticsearchUtil.searchDataById(indexName, id);
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
            boolQuery.must(QueryBuilders.termsQuery("threeCategoryId", req.getFThreeCategoryName()));
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
    private XContentBuilder createIndexMapping1(String indexName) {
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder().startObject().startObject("properties")
                    //.startObject("m_id").field("type","keyword").endObject()  //m_id:字段名,type:文本类型,analyzer 分词器类型
                    .startObject("skuId").field("type", "keyword").endObject()
                    .startObject("proSkuBrandId").field("type", "keyword").endObject()
                    .startObject("proSkuSpuId").field("type", "keyword").endObject()
                    .startObject("proSkuSpuCode").field("type", "keyword").endObject()
                    .startObject("proSkuSpuName").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").endObject()
                    .startObject("proSkuSkuCode").field("type", "keyword").endObject()
                    .startObject("proSkuSkuName").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").endObject()
                    .startObject("proSkuTitle").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").endObject()
                    .startObject("proSkuSubTitle").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_smart").endObject()
                    .startObject("threeCategoryId").field("type", "keyword").endObject()
                    .startObject("threeCategoryCode").field("type", "keyword").endObject()
                    .startObject("threeCategoryName").field("type", "keyword").endObject()
                    .startObject("skuSupplierId").field("type", "keyword").endObject()
                    .startObject("skuSupplierName").field("type", "keyword").endObject()
                    .startObject("skuState").field("type", "keyword").endObject()
                    .startObject("proSkuSkuPic").field("type", "keyword").endObject()
                    .startObject("skuValuationUnit").field("type", "keyword").endObject()
                    .startObject("skuIntroduce").field("type", "keyword").endObject()
                    .startObject("skuGmtCreateTime").field("type", "keyword").endObject()
                    .startObject("skuGmtModifyTime").field("type", "keyword").endObject()
                    .startObject("spuState").field("type", "keyword").endObject()
                    .startObject("proSpuSpuPic").field("type", "keyword").endObject()
                    .startObject("spuTitle").field("type", "keyword").endObject()
                    .startObject("attributeCode").field("type", "keyword").endObject()
                    .startObject("attributeName").field("type", "keyword").endObject()
                    .startObject("value_Name").field("type", "keyword").endObject()
                    .startObject("attributeId").field("type", "keyword").endObject()
                    .startObject("attributeValueId").field("type", "keyword").endObject()
                    .startObject("attributeMap").field("type", "keyword").endObject()
                    .startObject("attriType").field("type", "keyword").endObject()
                    .startObject("oneCategoryId").field("type", "keyword").endObject()
                    .startObject("oneCategoryCode").field("type", "keyword").endObject()
                    .startObject("oneCategoryName").field("type", "keyword").endObject()
                    .startObject("twoCategoryId").field("type", "keyword").endObject()
                    .startObject("twoCategoryCode").field("type", "keyword").endObject()
                    .startObject("twoCategoryName").field("type", "keyword").endObject()
                    .startObject("brandCode").field("type", "keyword").endObject()
                    .startObject("bBrandName").field("type", "keyword").endObject()
                    .startObject("brandLoge").field("type", "keyword").endObject()
                    .startObject("brandShorthand").field("type", "keyword").endObject()
                    .startObject("brandIntroduction").field("type", "keyword").endObject()
                    .startObject("fOneCategoryId").field("type", "keyword").endObject()
                    .startObject("fOneCategoryCode").field("type", "keyword").endObject()
                    .startObject("fOneCategoryName").field("type", "keyword").endObject()
                    .startObject("fTwoCategoryId").field("type", "keyword").endObject()
                    .startObject("fTwoCategoryCode").field("type", "keyword").endObject()
                    .startObject("fTwoCategoryName").field("type", "keyword").endObject()
                    .startObject("fThreeCategoryId").field("type", "keyword").endObject()
                    .startObject("fThreeCategoryCode").field("type", "keyword").endObject()
                    .startObject("fThreeCategoryName").field("type", "keyword").endObject()
                    .startObject("type").field("type", "keyword").endObject()
                    .startObject("fileUrl").field("type", "keyword").endObject()
                    .startObject("fileSortNumber").field("type", "keyword").endObject()
                    .startObject("regionalCode").field("type", "keyword").endObject()
                    .startObject("regionalName").field("type", "keyword").endObject()
                    .startObject("regionalScope").field("type", "keyword").endObject()
                    .startObject("skuSellPriceJson").field("type", "keyword").endObject()
                    .startObject("skuSellPriceType").field("type", "keyword").endObject()
                    .startObject("warehouseId").field("type", "keyword").endObject()
                    .startObject("warehouseName").field("type", "keyword").endObject()
                    .startObject("inventory").field("type", "keyword").endObject()
                    .startObject("regionalId").field("type", "keyword").endObject()
                    .startObject("skuRegionalName").field("type", "keyword").endObject()
                    .startObject("storeId").field("type", "keyword").endObject()
                    .startObject("categoryId").field("type", "keyword").endObject()
                    .startObject("storeName").field("type", "keyword").endObject()
                    .startObject("businessCategory").field("type", "keyword").endObject()
                    .startObject("mainProducts").field("type", "keyword").endObject()
                    .startObject("businessArea").field("type", "keyword").endObject()
                    .startObject("boothBusinessBoothCode").field("type", "keyword").endObject()
                    .startObject("customerProfilesLevel").field("type", "keyword").endObject()
                    .startObject("approveState").field("type", "keyword").endObject()
                    .startObject("enterpriseType").field("type", "keyword").endObject()
                    .startObject("storeInfoStoreQrCode").field("type", "keyword").endObject()
                    .startObject("gmtCreateTime").field("type", "keyword").endObject()
                    .startObject("warehouseCode").field("type", "keyword").endObject()
                    .startObject("warehouseType").field("type", "keyword").endObject()
                    .startObject("warehouseRegional").field("type", "keyword").endObject()
                    .startObject("detailedAddress").field("type", "keyword").endObject()
                    .startObject("skuAuxiliaryUnit").field("type", "keyword").endObject()
                    .startObject("boothScheduledTime").field("type", "keyword").endObject()
                    .startObject("proSkuSkuPicJson").field("type", "keyword").endObject()
                    .endObject().startObject("settings").field("number_of_shards", 3).field("number_of_replicas", 1)
                    .endObject().endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapping;
    }
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
                        builder.startObject("id"); { builder.field("type", "text"); }
                        builder.endObject();
                        builder.startObject("user_name"); { builder.field("type", "text"); }
                        builder.endObject();
                        builder.startObject("user_password"); { builder.field("type", "text"); }
                        builder.endObject();
                        builder.startObject("user_real_name"); { builder.field("type", "text"); }
                        builder.endObject();
                        builder.startObject("create_time"); { builder.field("type", "date"); }
                        builder.endObject();
                        builder.startObject("is_deleted"); { builder.field("type", "integer"); }
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



    public String testInsertOne(String c) throws IOException {
        if (!ElasticsearchUtil.isIndexExist(indexName)) {
            ElasticsearchUtil.createIndex(indexName, createIndexMapping( indexName));
        }
        Map<String,Object> map = new HashMap();
        map.put("id", "00b6d5612f734414d68088e359e4b008");// 主键id
        map.put("user_name", "zhangsan");// 用户名
        map.put("user_password", "123456");// 密码
        map.put("user_real_name", "张三");// 用户真实姓名
        //map.put("create_time", new Date());// 创建时间
        map.put("is_deleted", 0);// 是否删除

        JSONObject jsonObject = JSONObject.parseObject(map.toString());

        String id = ElasticsearchUtil.addData(jsonObject, indexName, "00b6d5612f734414d68088e359e4b008");
        if (StringUtils.isNotBlank(id)) {
            return "SaveProduct success.";
        } else {
            return "SaveProduct failure!";
        }
    }

    // 权重复杂相关查询 start
    /*@Override
    public List<City> searchCity(Integer pageNumber, Integer pageSize, String searchContent) {
        // 校验分页参数
        if (pageSize == null || pageSize <= 0) {
            pageSize = PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        LOGGER.info("\n searchCity: searchContent [" + searchContent + "] \n ");
        // 构建搜索查询
        SearchQuery searchQuery = getCitySearchQuery(pageNumber,pageSize,searchContent);
        LOGGER.info("\n searchCity: searchContent [" + searchContent + "] \n DSL  = \n " + searchQuery.getQuery().toString());
        Page<City> cityPage = cityRepository.search(searchQuery);
        return cityPage.getContent();
    }
    /**
     * 根据搜索词构造搜索查询语句
     *
     * 代码流程：
     *      - 权重分查询
     *      - 短语匹配
     *      - 设置权重分最小值
     *      - 设置分页参数
     *
     * @param pageNumber 当前页码
     * @param pageSize 每页大小
     * @param searchContent 搜索内容
     * @return
     */
    /*private SearchQuery getCitySearchQuery(Integer pageNumber, Integer pageSize,String searchContent) {
        // 短语匹配到的搜索词，求和模式累加权重分
        // 权重分查询 https://www.elastic.co/guide/c ... .html
        //   - 短语匹配 https://www.elastic.co/guide/c ... .html
        //   - 字段对应权重分设置，可以优化成 enum
        //   - 由于无相关性的分值默认为 1 ，设置权重分最小值为 10
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                                                                           .add(QueryBuilders.matchPhraseQuery("name", searchContent),
                                                                                ScoreFunctionBuilders.weightFactorFunction(1000))
                                                                           .add(QueryBuilders.matchPhraseQuery("description", searchContent),
                                                                                ScoreFunctionBuilders.weightFactorFunction(500))
                                                                           .scoreMode(SCORE_MODE_SUM).setMinScore(MIN_SCORE);
        // 分页参数
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }*/
    // 权重复杂相关查询 end


}