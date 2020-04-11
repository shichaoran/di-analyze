package com.vd.canary.data.common.es.repository;

import com.vd.canary.data.common.es.index.ShopTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ShopTORepository extends ElasticsearchRepository<ShopTO, String> {
}
