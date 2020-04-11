package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.AttributeValueFeign;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author WangRuilin
 * @Date 2020/4/11 10:47
 */
public class AttributeValue implements Function {

    private static final Logger logger = LoggerFactory.getLogger(AttributeValue.class);
    @Autowired
    private AttributeValueFeign attributeValueFeign;
    @Override
    public void performES(String msg) {
        logger.info("AttributeValue.msg"+msg);
        ProductsTO productsTO = new ProductsTO();

        ResponseBO<?> res = attributeValueFeign.get(productsTO.getAttributeValueId());
        String pro =  (String) res.getData();

        productsTO.setValue_Name(pro);
        productsTO.setValue_Name("红色");
    }
}
