package com.vd.canary.data.common.es.service.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.vd.canary.data.common.es.repository.ProductRepository;
import com.vd.canary.data.common.es.service.ProductESService;
import com.vd.canary.data.constants.Constant;
import com.vd.canary.data.util.JsonUtils;
import lombok.Data;
import org.elasticsearch.action.update.UpdateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 商品 ES 业务逻辑实现类
 *
 */
@Data
@Service
public class ProductESServiceImpl implements ProductESService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 新增商品信息
     * @param product
     * @return
     */
    @Override
    public void saveProduct(Product product) {

        productRepository.save(product);
    }

    /**
     * 批量新增商品信息
     **/
    @Override
    public void batchAddProduct(List<Product> products) {
        if(CollectionUtils.isEmpty(products)) {
            return ;
        }
        List<IndexQuery> queries = Lists.newArrayListWithExpectedSize(products.size());
        IndexQuery indexItem  = null;
        for(Product product :products) {
            indexItem = new IndexQuery();
            indexItem.setObject(product);
            queries.add(indexItem);
        }
        elasticsearchTemplate.bulkIndex(queries);
    }

    //
    @Override
    public void deletedUserById(String id) {
        productRepository.deleteById(id);
    }

    /**
     * 根据productId更新信息
     */
    @Override
    public void updateProduct(Product product) {
        UpdateQuery updateQuery = new UpdateQuery();
        updateQuery.setId(product.getProductId().toString());
        updateQuery.setClazz(Product.class);
        product.setProductId(null);
        UpdateRequest request = new UpdateRequest();
        try{
            request.doc(JsonUtils.beanToJson(product));
        }catch (Exception e){
        }
        updateQuery.setUpdateRequest(request);
        elasticsearchTemplate.update(updateQuery);
    }

    @Override
    public List<Product> queryByProductName(String userName) {

        return productRepository.findByUserNameLike(userName);
    }

    public List<Product> findByDescriptionAndScore(String description, Integer score) {
        return productRepository.findByDescriptionAndScore(description, score);
    }

    public List<Product> findByDescriptionOrScore(String description, Integer score) {
        return productRepository.findByDescriptionOrScore(description, score);
    }

    public List<Product> findByDescription(Integer pageNumber, Integer pageSize,String description) {
        // 校验分页参数
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constant.ES_PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < Constant.ES_DEFAULT_PAGE_NUMBER) {
            pageNumber = Constant.ES_DEFAULT_PAGE_NUMBER;
        }

        //LOGGER.info("\n searchCity: searchContent [" + searchContent + "] \n ");
        //
        //// 构建搜索查询
        //SearchQuery searchQuery = getCitySearchQuery(pageNumber, pageSize, searchContent);
        //
        //LOGGER.info("\n searchCity: searchContent [" + searchContent + "] \n DSL  = \n " + searchQuery.getQuery().toString());
        //
        //Page<City> cityPage = cityRepository.search(searchQuery);
        //return cityPage.getContent();
        //
        //// 获取分页参数
        //Pageable pageable = PageRequest.of(pageNumber, pageSize);

        //return productRepository.findByDescription(description, pageable).getContent();
        return null;
    }

    //public List<Product> findByDescriptionNot(String description) {
    //    return productRepository.findByDescriptionNot(description, pageable).getContent();
    //}
    //
    //public List<Product> findByDescriptionLike(String description) {
    //    return productRepository.findByDescriptionLike(description, pageable).getContent();
    //}


}
