package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.obmp.product.api.feign.AttributeManagementFeign;
import net.sf.jsqlparser.expression.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author WangRuilin
 * @Date 2020/4/10 13:55
 */
public class AttributeManagement extends Function {

    private static final Logger logger = LoggerFactory.getLogger(AttributeManagement.class);
    @Autowired
    private AttributeManagementFeign attributeManagementFeign;

    ProductsTO productsTO = new ProductsTO();

//    ResponseBO<AttributeManagementResp> res = attributeManagementFeign.listAttributeManagementsByCondition(productsTO.getAttributeId());
//    ProductSpuDetailResp pro =  (ProductSpuDetailResp)res.getData();
//
//        productsTO.setSpuState(pro.getSpuState());
//        productsTO.setProSpuSpuPic(pro.getSpuPic());
//        productsTO.setSpuTitle(pro.getSpuTitle());

    }
