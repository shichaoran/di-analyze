package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.AttributeValueRelationsFeign;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author WangRuilin
 * @Date 2020/4/11 10:57
 */
public class AttributeValueRelations implements Function {

    private static final Logger logger = LoggerFactory.getLogger(AttributeValueRelations.class);
    @Autowired
    private AttributeValueRelationsFeign attributeValueRelationsFeign;
    @Override
    public void performES(String msg) {

        logger.info("AttributeValueRelations.msg"+msg);
        ProductsTO productsTO = new ProductsTO();

//        ResponseBO<ProductSpuDetailResp> res = attributeValueRelationsFeign.spuDetail(productsTO.getProSkuSpuId());
//        ProductSpuDetailResp pro =  (ProductSpuDetailResp)res.getData();

        productsTO.setAttributeId("111");
        productsTO.setAttributeValueId("111");
        Map<String, HashSet<String>> map= new HashMap<String, HashSet<String>>();
        HashSet set = new HashSet<String>();
        set.add("1");
        map.put("s",set);
        productsTO.setAttributeMap(map);
        productsTO.setAttriType("1");

    }
}
