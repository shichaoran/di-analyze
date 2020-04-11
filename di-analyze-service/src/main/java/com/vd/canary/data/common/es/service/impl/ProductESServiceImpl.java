package com.vd.canary.data.common.es.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.es.repository.ProductTORepository;
import com.vd.canary.data.common.es.service.ProductESService;
import com.vd.canary.data.constants.Constant;
import com.vd.canary.data.util.JsonUtils;
import com.vd.canary.utils.DateUtil;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品 ES 业务逻辑实现类
 *
 */
@Data
@Service
public class ProductESServiceImpl implements ProductESService {

    @Autowired
    private ProductTORepository productRepository;

    @Autowired
    //private ElasticsearchTemplate elasticsearchTemplate;
    private ElasticsearchRestTemplate elasticsearchTemplate;

    //新增商品信息
    @Override
    public void saveProduct(ProductsTO product) {

        productRepository.save(product);
    }

    // 批量新增商品信息
    @Override
    public void batchAddProduct(List<ProductsTO> products) {
        if(CollectionUtils.isEmpty(products)) {
            return ;
        }
        List<IndexQuery> queries = Lists.newArrayListWithExpectedSize(products.size());
        IndexQuery indexItem  = null;
        for(ProductsTO product :products) {
            indexItem = new IndexQuery();
            indexItem.setObject(product);
            queries.add(indexItem);
        }
        elasticsearchTemplate.bulkIndex(queries);
    }

    //删除商品信息
    @Override
    public void deletedProductById(String id) {
        productRepository.deleteById(id);
    }

    /**
     * 根据productId更新信息
     */
    @Override
    public void updateProduct(ProductsTO product) {
        UpdateQuery updateQuery = new UpdateQuery();
        updateQuery.setId(product.getSkuId().toString());
        updateQuery.setClazz(ProductsTO.class);
        product.setSkuId(null);
        UpdateRequest request = new UpdateRequest();
        try{
            request.doc(JsonUtils.beanToJson(product));
        }catch (Exception e){
        }
        updateQuery.setUpdateRequest(request);
        elasticsearchTemplate.update(updateQuery);
    }

    public ProductsTO findById(String id) {
        Optional<ProductsTO> productOptional= productRepository.findById(id);
        return productOptional.get();
    }

    public Iterator<ProductsTO> findAll() {
        return productRepository.findAll().iterator();
    }


    /**
     * 功能：首页顶部商品搜索框通过 关键字 查询
     * must 多条件 &（并且）   mustNot 多条件 != (非)   should 多条件 || (或)
     */
    /*public List<ProductsTO> boolQueryByKeyword(String keyword) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //判断订单号
        if (StringUtils.isNotBlank(wayBill.getWayBillNum())){
            WildcardQueryBuilder wayBillNum = QueryBuilders.wildcardQuery("wayBillNum", "*" + wayBill.getWayBillNum() + "*");
            boolQuery.must(wayBillNum);
        }
        // 2 发货地  分词
        if (StringUtils.isNotBlank(wayBill.getSendAddress())){
            // 2 改进的第一个问题：北京市顺义区   北  京  市  北京  顺义 义区   这种方式允许查吗？
            WildcardQueryBuilder sendAddressequeryBuilder1 = QueryBuilders.wildcardQuery("sendAddress", "*" + wayBill.getSendAddress() + "*");
            // 3 改进的第二个问题：北京顺义    这种方式要被允许-->这种方式需要对 查询词条进行分词后   再进行查询
            // QueryStringQueryBuilder此对象会对查询的词条分词后的各种情况进行分词查找
            // 参数：就是前台传过来的查询内容
            // QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress());
//            queryStringQueryBuilder.field("sendAddress").defaultOperator(Operator.AND);
            QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress()).field("sendAddress").defaultOperator(Operator.AND);
            BoolQueryBuilder should = QueryBuilders.boolQuery().should(sendAddressequeryBuilder1).should(queryStringQueryBuilder);
            boolQuery.must(should);
        }
        //收货地址
        if (StringUtils.isNotBlank(wayBill.getRecAddress())){
            WildcardQueryBuilder recAddressQuery = QueryBuilders.wildcardQuery("recAddress", "*" + wayBill.getRecAddress() + "*");

            //改进   允许对词条分词后  在查询
            QueryStringQueryBuilder recAddressQueryString = new QueryStringQueryBuilder(wayBill.getRecAddress()).field("recAddress").defaultOperator(Operator.AND);

            //取并集
            BoolQueryBuilder should = QueryBuilders.boolQuery().should(recAddressQuery).should(recAddressQueryString);
            boolQuery.must(should);
        }
        //4 速运当日、速运次日、速运隔日 sds
        if (StringUtils.isNotBlank(wayBill.getSendProNum())){
            TermQueryBuilder sendProNumQuery = QueryBuilders.termQuery("sendProNum", wayBill.getSendProNum());
            boolQuery.must(sendProNumQuery);
        }
        //5 运单状态： 1 待发货、 2 派送中、3 已签收、4 异常
        if (wayBill.getSignStatus()!=null&& wayBill.getSignStatus()!=0){
            TermQueryBuilder signStatus = QueryBuilders.termQuery("signStatus", wayBill.getSignStatus());
            boolQuery.must(signStatus);
        }
        // 执行分页
        queryBuilder.withPageable(PageRequest.of(page-1,rows));
        // 执行查询
        queryBuilder.withQuery(boolQuery);
        Page<ESWayBill> list = wayBillRepository.search(queryBuilder.build());
        //拼接结果
        DatagridResult result = new DatagridResult();
        result.setTotal(list.getTotalElements());
        result.setRows(list.getContent());
        return result;




        QueryBuilder query = QueryBuilders.boolQuery()
                                          .must(QueryBuilders.termQuery("userId","2019040499"))  //精确查询
                                          .must(QueryBuilders.wildcardQuery("userName","*华腾"))    //模糊查询
                                          .must(QueryBuilders.rangeQuery("createDate")
                                                             .from(DateUtil.getDateTimeString())
                                                             .to(DateUtil.getDateTimeString()));  //范围查询
        List<String> properties = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");//排序
        //多条件排序
        //Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"userId");
        // Sort.Order order2 = new Sort.Order(Sort.Direction.ASC,"userId");
        //Sort sortList = Sort.by(order1,order2);
        //Pageable pageable = PageRequest.of(0, 10, sort);
        //Page<User> userPage = userReposity.search(query, pageable);
        //List<User> pageContent= userPage.getContent();






        //BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //boolQueryBuilder.should(QueryBuilders.termQuery("proSkuSpuName",keyword));
        //boolQueryBuilder.should(QueryBuilders.termQuery("proSkuSkuName",keyword));
        //boolQueryBuilder.should(QueryBuilders.termQuery("proSkuTitle",keyword));
        //boolQueryBuilder.should(QueryBuilders.termQuery("proSkuSubTitle",keyword));
        //boolQueryBuilder.should(QueryBuilders.termQuery("skuSupplierName",keyword));
        ////boolQueryBuilder.must(QueryBuilders.termQuery("storeName.keyword",keyword));
        //SearchQuery searchQuery = new NativeSearchQueryBuilder()
        //        .withIndices("my_index")
        //        .withTypes("my_type")
        //        .withQuery(boolQueryBuilder)
        //        .build();
        //List<ProductsTO> list = elasticsearchTemplate.queryForList(searchQuery,ProductsTO.class);
        //return list;
    }




    public Page<ProductsTO> findByContent(Integer pageNumber, Integer pageSize,String content) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constant.ES_PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < Constant.ES_DEFAULT_PAGE_NUMBER) {
            pageNumber = Constant.ES_DEFAULT_PAGE_NUMBER;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByContent(content,pageable);
    }


    public Page<ProductsTO> findByFirstCode(Integer pageNumber, Integer pageSize, String firstCode) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constant.ES_PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < Constant.ES_DEFAULT_PAGE_NUMBER) {
            pageNumber = Constant.ES_DEFAULT_PAGE_NUMBER;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByFirstCode(firstCode,pageable);
    }


    public Page<ProductsTO> findBySecordCode(Integer pageNumber, Integer pageSize, String secordCode) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constant.ES_PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < Constant.ES_DEFAULT_PAGE_NUMBER) {
            pageNumber = Constant.ES_DEFAULT_PAGE_NUMBER;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findBySecordCode(secordCode,pageable);
    }


    public Page<ProductsTO> query(Integer pageNumber, Integer pageSize, String key) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constant.ES_PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < Constant.ES_DEFAULT_PAGE_NUMBER) {
            pageNumber = Constant.ES_DEFAULT_PAGE_NUMBER;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByContent(key,pageable);
    }




    //获取分页参数 Pageable pageable = PageRequest.of(pageNumber, pageSize);
    public List<ProductsTO> queryByProductName(Integer pageNumber, Integer pageSize,String keyword) {
        // 校验分页参数
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constant.ES_PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < Constant.ES_DEFAULT_PAGE_NUMBER) {
            pageNumber = Constant.ES_DEFAULT_PAGE_NUMBER;
        }
        //构建查询格式：NativeSearchQueryBuilder.withQuery(QueryBuilder1).withFilter(QueryBuilder2).withSort(SortBuilder1).withXXXX().build();
        //SearchQuery searchQuery =
        //        new NativeSearchQueryBuilder().withQuery(QueryBuilders.queryStringQuery("spring boot OR 书籍")).build();



        // 构建搜索查询
        SearchQuery searchQuery = getCitySearchQuery(pageNumber,pageSize,searchContent);
        Page<City> cityPage = cityRepository.search(searchQuery);
        return cityPage.getContent();




        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Integer offset = (pageNumber-1)*pageSize;
        System.out.println(offset+"---"+pageSize);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(str))
                                                                .withPageable(pageable).build();
        return elasticsearchTemplate.queryForPage(searchQuery,ProductsTO.class);





        return productRepository.findByDescription(description, pageable).getContent();

        return productRepository.findByUserNameLike(userName);
    }



    public void  search(){
        double lat = 39.929986;
        double lon = 116.395645;

        //查询某经纬度100米范围内
        GeoDistanceQueryBuilder builder = QueryBuilders.geoDistanceQuery("address").point(lat, lon)
                                                       .distance(100, DistanceUnit.METERS);

        GeoDistanceSortBuilder sortBuilder = SortBuilders.geoDistanceSort("address")
                                                         .point(lat, lon)
                                                         .unit(DistanceUnit.METERS)
                                                         .order(SortOrder.ASC);

        Pageable pageable = new PageRequest(0, 50);

        NativeSearchQueryBuilder builder1 = new NativeSearchQueryBuilder().withFilter(builder).withSort(sortBuilder).withPageable(pageable);
        SearchQuery searchQuery = builder1.build();
    }



    public List<ProductsTO> findByDescription(Integer pageNumber, Integer pageSize,String description) {
        // 校验分页参数
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constant.ES_PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < Constant.ES_DEFAULT_PAGE_NUMBER) {
            pageNumber = Constant.ES_DEFAULT_PAGE_NUMBER;
        }

        LOGGER.info("\n searchCity: searchContent [" + searchContent + "] \n ");

        // 构建搜索查询
        SearchQuery searchQuery = getCitySearchQuery(pageNumber, pageSize, searchContent);

        LOGGER.info("\n searchCity: searchContent [" + searchContent + "] \n DSL  = \n " + searchQuery.getQuery().toString());

        Page<City> cityPage = cityRepository.search(searchQuery);
        return cityPage.getContent();

        // 获取分页参数
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return productRepository.findByDescription(description, pageable).getContent();
    }*/


    /*//聚合查询
    public Iterable querySum() {
        // 创建查询体
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //设置聚合条件
        RangeQueryBuilder query = QueryBuilders.rangeQuery("age").from("30").to("60");
        //将聚合条件设置入查询体之中
        builder.must(query);
        Iterable<Employee> search = employeeRepository.search(builder);
        return search;
    }*/

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