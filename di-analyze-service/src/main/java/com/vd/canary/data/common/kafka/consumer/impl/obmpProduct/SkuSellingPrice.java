/*
package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;
import com.vd.canary.obmp.product.api.feign.SkuSellingPriceFeign;
import com.vd.canary.obmp.product.api.response.sku.vo.SkuSellingPriceVO;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

*/
/**
 * @Author WangRuilin
 * @Date 2020/4/11 9:22
 *//*

public class SkuSellingPrice implements Function {

    private static final Logger logger = LoggerFactory.getLogger(SkuSellingPrice.class);
    @Autowired
    private SkuSellingPriceFeign skuSellingPriceFeign;
    @Override
    public void performES(String msg) {
        logger.info("SkuSellingPrice.msg"+msg);
        ProductsTO productsTO = new ProductsTO();

        ResponseBO<SkuSellingPriceVO> res = skuSellingPriceFeign.get(productsTO.getSkuId());
//        SkuSellingPriceVO pro =  (SkuSellingPriceVO)res.getData();
//
//        productsTO.setSkuSellPriceJson(pro.getPriceJson().toString());
//        productsTO.setSkuSellPriceType(pro.getPriceType());


    }
}
*/
