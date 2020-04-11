package com.vd.canary.data.common.es.service.impl;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.vd.canary.data.common.es.index.ShopTO;
import com.vd.canary.data.common.es.repository.ShopTORepository;
import com.vd.canary.data.util.JsonUtils;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.update.UpdateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

@Data
@Service
public class ShopESServiceImpl {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ShopTORepository shopRepository;

    //新增店铺信息
    public void saveProduct(ShopTO shop) {
        shopRepository.save(shop);
    }

    // 批量新增店铺信息
    public void batchAddShop(List<ShopTO> shops) {
        if(CollectionUtils.isEmpty(shops)) {
            return ;
        }
        List<IndexQuery> queries = Lists.newArrayListWithExpectedSize(shops.size());
        IndexQuery indexItem  = null;
        for(ShopTO shop :shops) {
            indexItem = new IndexQuery();
            indexItem.setObject(shop);
            queries.add(indexItem);
        }
        elasticsearchTemplate.bulkIndex(queries);
    }

    //删除店铺信息
    public void deletedShopById(String id) {
        shopRepository.deleteById(id);
    }

    // 根据Id更新店铺信息
    public void updateShop(ShopTO shop) {
        UpdateQuery updateQuery = new UpdateQuery();
        updateQuery.setId(shop.getId().toString());
        updateQuery.setClazz(ShopTO.class);
        shop.setId(null);
        UpdateRequest request = new UpdateRequest();
        try{
            request.doc(JsonUtils.beanToJson(shop));
        }catch (Exception e){
        }
        updateQuery.setUpdateRequest(request);
        elasticsearchTemplate.update(updateQuery);
    }

    public ShopTO findById(String id) {
        Optional<ShopTO> shopOptional= shopRepository.findById(id);
        return shopOptional.get();
    }

}