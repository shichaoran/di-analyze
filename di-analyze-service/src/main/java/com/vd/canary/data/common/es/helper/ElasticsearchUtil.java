package com.vd.canary.data.common.es.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ElasticsearchUtil<T> {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static RestHighLevelClient client;

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * spring容器初始化的时候执行该方法
     */
    @PostConstruct
    public void initClient() {
        client = this.restHighLevelClient;
    }


    /**
     * 判断索引是否存在     *
     *
     * @param index 索引，类似数据库
     * @return boolean
     */
    public static boolean isIndexExist(String index) {
        boolean exists = false;
        try {
            exists = client.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exists) {
            log.info("Index [" + index + "] is exist!");
        } else {
            log.info("Index [" + index + "] is not exist!");
        }
        return exists;
    }

    /**
     * 创建索引以及映射mapping，并给索引某些字段指定iK分词，以后向该索引中查询时，就会用ik分词。
     *
     * @param: indexName  索引，类似数据库
     * @return: boolean
     */
    public static boolean createIndex(String indexName) {
        if (!isIndexExist(indexName)) {
            log.info("Index is not exits!");
        }
        CreateIndexResponse createIndexResponse = null;
        try {
            //创建映射
            XContentBuilder mapping = null;
            try {
                mapping = XContentFactory.jsonBuilder()
                        .startObject()
                        .startObject("properties")
                        //.startObject("m_id").field("type","keyword").endObject()  //m_id:字段名,type:文本类型,analyzer 分词器类型
                        //该字段添加的内容，查询时将会使用ik_max_word 分词 //ik_smart  ik_max_word  standard
                        .startObject("id")
                        .field("type", "text")
                        .endObject()
                        .startObject("pdfId")
                        .field("type", "text")
                        .endObject()
                        .startObject("title")
                        .field("type", "text")
                        .field("analyzer", "ik_max_word")
                        .endObject()
                        .startObject("author")
                        .field("type", "text")
                        .field("analyzer", "ik_max_word")
                        .endObject()
                        .startObject("content")
                        .field("type", "text")
                        .field("analyzer", "ik_max_word")
                        .endObject()
                        .startObject("columnName")
                        .field("type", "text")
                        .field("analyzer", "ik_max_word")
                        .endObject()
                        .startObject("articlesSource")
                        .field("type", "text")
                        .field("analyzer", "ik_max_word")
                        .endObject()
                        .startObject("periodicalDate")
                        .field("type", "text")
                        // .field("type", "date")
                        .field("analyzer", "ik_max_word")
                        // .field("format", "yyyy-MM")
                        .endObject()
                        .endObject()
                        .startObject("settings")
                        //分片数
                        .field("number_of_shards", 3)
                        //副本数
                        .field("number_of_replicas", 1)
                        .endObject()
                        .endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CreateIndexRequest request = new CreateIndexRequest(indexName).source(mapping);
            //设置创建索引超时2分钟
            request.setTimeout(TimeValue.timeValueMinutes(2));
            createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createIndexResponse.isAcknowledged();
    }

    public static boolean existById(String index, String id) throws IOException {
        GetRequest getRequest = new GetRequest(index, id);
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        log.info("exists: {}", exists);
        return exists;
    }

    /**
     * 数据添加
     *
     * @param content   要增加的数据
     * @param indexName 索引，类似数据库
     * @param id        id
     * @return String
     */
    public static String addData(JSONObject content, String indexName, String id) throws IOException {
        IndexResponse response = null;
        IndexRequest request = new IndexRequest(indexName).id(id).source(mapper.writeValueAsString(content), XContentType.JSON);
        response = client.index(request, RequestOptions.DEFAULT);
        log.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }
    public static String addData(XContentBuilder content, String indexName, String id) throws IOException {
        IndexResponse response = null;
        IndexRequest request = new IndexRequest(indexName).id(id).source(content);
        response = client.index(request, RequestOptions.DEFAULT);
        log.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 数据修改
     *
     * @param content   要修改的数据
     * @param indexName 索引，类似数据库
     * @param id        id
     * @return String
     */
    public static String updateData(Map<String, Object> content, String indexName, String id) throws IOException {
        UpdateResponse response = null;
        UpdateRequest request = new UpdateRequest(indexName, id).doc(content);
        response = client.update(request, RequestOptions.DEFAULT);
        log.info("updateData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }
    public static String updateData(XContentBuilder content, String indexName, String id) throws IOException {
        UpdateResponse response = null;
        UpdateRequest request = new UpdateRequest(indexName, id).doc(content);
        response = client.update(request, RequestOptions.DEFAULT);
        log.info("updateData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 根据条件删除
     *
     * @param builder   要删除的数据  new TermQueryBuilder("userId", userId)
     * @param indexName 索引，类似数据库
     * @return
     */
    public static void deleteByQuery(String indexName, QueryBuilder builder) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            client.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除记录
     *
     * @param indexName
     * @param id
     */
    public static void deleteData(String indexName, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("delete:{}" , JSON.toJSONString(response));
    }

    /**
     * 清空记录
     *
     * @param indexName
     */
    public static void clear(String indexName) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName);
        DeleteResponse response = null;
        try {
            response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("delete: {}" ,JSON.toJSONString(response));
    }

    /**
     * 使用分词查询  高亮 排序 ,并分页
     *
     * @param index          索引名称
     * @param startPage      当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return 结果
     */
    public static EsPage searchDataPage(String index, Integer startPage, Integer pageSize, QueryBuilder query, String fields, String sortField, String highlightField) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置一个可选的超时，控制允许搜索的时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotBlank(fields)) {
            searchSourceBuilder.fetchSource(fields, null);
        }
        //排序字段
        if (StringUtils.isNotBlank(sortField)) {
            searchSourceBuilder.sort(new FieldSortBuilder(sortField).order(SortOrder.ASC));
        }
        // 高亮（xxx=111,aaa=222）
        if (StringUtils.isNotBlank(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            //设置前缀
            highlightBuilder.preTags("<span style='color:red' >");
            //设置后缀
            highlightBuilder.postTags("</span>");
            String[] split = highlightField.split(",");
            if (split.length > 0) {
                for (String s : split) {
                    HighlightBuilder.Field highlight = new HighlightBuilder.Field(s);
                    //荧光笔类型
                    highlight.highlighterType("unified");
                    //TODO
                    highlight.fragmentSize(150);
                    //从第3个分片开始获取高亮片段
                    highlight.numOfFragments(3);
                    // 设置高亮字段
                    highlightBuilder.field(highlight);
                }
            }
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        // 设置是否按查询匹配度排序
        searchSourceBuilder.explain(true);
        if (startPage <= 0) {
            startPage = 0;
        }
        //如果 pageSize是10 那么startPage>9990 (10000-pagesize) 如果 20  那么 >9980 如果 50 那么>9950
        //深度分页  TODO
        if (startPage > (10000 - pageSize)) {
            searchSourceBuilder.query(query);
            searchSourceBuilder
                    // .setScroll(TimeValue.timeValueMinutes(1))
                    .size(10000);
            //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
            // LOGGER.info("\n{}", searchSourceBuilder);
            // 执行搜索,返回搜索响应信息
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = null;
            try {
                searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            long totalHits = searchResponse.getHits().getTotalHits().value;
            if (searchResponse.status().getStatus() == 200) {
                //使用scrollId迭代查询
                List<Map<String, Object>> result = disposeScrollResult(searchResponse, highlightField);
                List<Map<String, Object>> sourceList = result.stream().parallel().skip((startPage - 1 - (10000 / pageSize)) * pageSize).limit(pageSize).collect(Collectors.toList());
                return new EsPage(startPage, pageSize, (int) totalHits, sourceList);
            }
        } else {//浅度分页
            searchSourceBuilder.query(query);
            // 分页应用
            searchSourceBuilder
                    //设置from确定结果索引的选项以开始搜索。默认为0
                    .from((startPage - 1) * pageSize)
                    //设置size确定要返回的搜索匹配数的选项。默认为10
                    .size(pageSize);
            //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
            // LOGGER.info("\n{}", searchSourceBuilder);
            // 执行搜索,返回搜索响应信息
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = null;
            try {
                searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            long totalHits = searchResponse.getHits().getTotalHits().value;
            long length = searchResponse.getHits().getHits().length;
            // LOGGER.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);
            if (searchResponse.status().getStatus() == 200) {
                // 解析对象
                List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);
                return new EsPage(startPage, pageSize, (int) totalHits, sourceList);
            }
        }
        return null;
    }

    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse 搜索的结果集
     * @param highlightField 高亮字段
     */
    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, Object> resultMap = getResultMap(searchHit, highlightField);
            sourceList.add(resultMap);
        }
        return sourceList;
    }


    /**
     * 获取高亮结果集
     *
     * @param: [hit, highlightField]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     */
    private static Map<String, Object> getResultMap(SearchHit hit, String highlightField) {
        hit.getSourceAsMap().put("id", hit.getId());
        if (StringUtils.isNotBlank(highlightField)) {
            String[] split = highlightField.split(",");
            if (split.length > 0) {
                for (String str : split) {
                    HighlightField field = hit.getHighlightFields().get(str);
                    if (null != field) {
                        Text[] text = field.getFragments();
                        String hightStr = null;
                        //TODO
                        if (text != null) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (Text str1 : text) {
                                String s = str1.string();
                                //s.replaceAll("<p>", "<span>").replaceAll("</p>", "</span>");
                                stringBuffer.append(s);
                            }
                            hightStr = stringBuffer.toString();
                            //遍历 高亮结果集，覆盖 正常结果集
                            hit.getSourceAsMap().put(str, hightStr);
                        }
                    }
                }
            }
        }
        return hit.getSourceAsMap();
    }


    public static <T> List<T> search(String index, SearchSourceBuilder builder, Class<T> c) {
        SearchRequest request = new SearchRequest(index);
        request.source(builder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), c));
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理scroll结果
     *
     * @param: [response, highlightField]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    private static List<Map<String, Object>> disposeScrollResult(SearchResponse response, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<>();
        //使用scrollId迭代查询
        while (response.getHits().getHits().length > 0) {
            String scrollId = response.getScrollId();
            try {
                response = client.scroll(new SearchScrollRequest(scrollId), RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits.getHits()) {
                Map<String, Object> resultMap = getResultMap(hit, highlightField);
                sourceList.add(resultMap);
            }
        }
        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId(response.getScrollId());
        try {
            client.clearScroll(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceList;
    }


}
