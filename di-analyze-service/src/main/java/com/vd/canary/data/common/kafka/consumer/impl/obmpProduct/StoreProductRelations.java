package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;
import com.vd.canary.obmp.product.api.feign.StoreProductRelationsFeign;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import com.vd.canary.obmp.product.api.response.store.vo.StoreProductRelationsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author WangRuilin
 * @Date 2020/4/11 9:55
 */
public class StoreProductRelations implements Function {

    private static final Logger logger = LoggerFactory.getLogger(StoreProductRelations.class);
    @Autowired
    private StoreProductRelationsFeign storeProductRelationsFeign;
    @Override
    public void performES(String msg) {

        logger.info("StoreProductRelations.msg"+msg);
        ProductsTO productsTO = new ProductsTO();

        ResponseBO<StoreProductRelationsVO> res = storeProductRelationsFeign.get(productsTO.getSkuId());
        StoreProductRelationsVO pro =  (StoreProductRelationsVO)res.getData();

        productsTO.setStoreId(pro.getStoreId());
        productsTO.setCategoryId(pro.getCategoryId());

    }
}
