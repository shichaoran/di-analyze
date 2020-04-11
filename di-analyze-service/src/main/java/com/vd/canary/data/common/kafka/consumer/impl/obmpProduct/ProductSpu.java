package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;
import com.vd.canary.obmp.product.api.response.spu.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author WangRuilin
 * @Date 2020/4/9 14:22
 */
public class ProductSpu implements Function {
    private static final Logger logger = LoggerFactory.getLogger(ProductSpu.class);
    @Autowired
    private ProductSpuFeign productspu;
    @Override
    public void performES(String msg) {
        logger.info("ProductSpu.msg"+msg);
//        ProductsTO productsTO = new ProductsTO();
//
//        ResponseBO<ProductSpuDetailResp> res = productspu.spuDetail(productsTO.getProSkuSpuId());
//        ProductSpuDetailResp pro =  (ProductSpuDetailResp)res.getData();
//
//        productsTO.setSpuState(pro.getSpuState());
//        productsTO.setProSpuSpuPic(pro.getSpuPic());
//        productsTO.setSpuTitle(pro.getSpuTitle());


    }


}
