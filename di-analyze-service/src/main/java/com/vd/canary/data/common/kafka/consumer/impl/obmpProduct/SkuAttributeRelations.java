package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.service.es.impl.ProductsTO;
import com.vd.canary.obmp.product.api.feign.AttributeManagementFeign;
import com.vd.canary.obmp.product.api.feign.SkuAttributeRelationsFeign;
import com.vd.canary.obmp.product.api.response.attribute.AttributeManagementResp;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author WangRuilin
 * @Date 2020/4/10 14:07
 */
public class SkuAttributeRelations implements Function {

    private static final Logger logger = LoggerFactory.getLogger(SkuAttributeRelations.class);

    @Autowired
    private SkuAttributeRelationsFeign skuAttributeRelationsFeign;

    @Override
    public void performES(String msg) {
        logger.info("ProductSpu.msg" + msg);
        ProductsTO productsTO = new ProductsTO();
        ResponseBO<?> res = skuAttributeRelationsFeign.get(productsTO.getSkuId());

        List list = (List)res.getData();
        productsTO.setAttributeId(list.toString());

    }
}
