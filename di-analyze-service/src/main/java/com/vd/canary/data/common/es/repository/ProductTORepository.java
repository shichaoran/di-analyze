package com.vd.canary.data.common.es.repository;

import java.util.List;
import com.vd.canary.data.common.es.index.ProductsTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
public interface ProductTORepository extends ElasticsearchRepository<ProductsTO, String> {

    //@Query("{\"bool\" : {\"must\" : {\"field\" : {\"content\" : \"?\"}}}}")
    //Page<ProductsTO> findByContent(String content, Pageable pageable);

    //@Query("{\"bool\" : {\"must\" : {\"field\" : {\"firstCode.keyword\" : \"?\"}}}}")
    //Page<ProductsTO> findByFirstCode(String firstCode, Pageable pageable);

    //@Query("{\"bool\" : {\"must\" : {\"field\" : {\"secordCode.keyword\" : \"?\"}}}}")
    //Page<ProductsTO> findBySecordCode(String secordCode, Pageable pageable);


    //按userName like查询
    //List<ProductsTO> findByUserNameLike(String userName);

    // AND 语句查询
    //List<ProductsTO> findByDescriptionAndScore(String description, Integer score);

    //OR 语句查询
    //List<ProductsTO> findByDescriptionOrScore(String description, Integer score);

    /*查询城市描述,等同于下面代码
     * @Query("{\"bool\" : {\"must\" : {\"term\" : {\"description\" : \"?0\"}}}}")
     */
    //Page<ProductsTO> findByDescription(String description, Pageable page);

    //NOT 语句查询
    //Page<ProductsTO> findByDescriptionNot(String description, Pageable page);

    //LIKE 语句查询
    //Page<ProductsTO> findByDescriptionLike(String description, Pageable page);

}
