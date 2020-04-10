package com.vd.canary.data.common.es.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    //按userName like查询
    List<Product> findByUserNameLike(String userName);

    // AND 语句查询
    List<Product> findByDescriptionAndScore(String description, Integer score);

    //OR 语句查询
    List<Product> findByDescriptionOrScore(String description, Integer score);

    /*查询城市描述,等同于下面代码
     * @Query("{\"bool\" : {\"must\" : {\"term\" : {\"description\" : \"?0\"}}}}")
     */
    Page<Product> findByDescription(String description, Pageable page);

    //NOT 语句查询
    Page<Product> findByDescriptionNot(String description, Pageable page);

    //LIKE 语句查询
    Page<Product> findByDescriptionLike(String description, Pageable page);

}
