//package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;
//
//import com.vd.canary.core.bo.ResponseBO;
//import com.vd.canary.data.api.response.es.ProductsRes;
//import com.vd.canary.data.api.response.es.ShopRes;
//import com.vd.canary.data.common.es.index.ProductsTO;
//import com.vd.canary.data.common.kafka.consumer.impl.Function;
//import com.vd.canary.obmp.product.api.feign.AttributeManagementFeign;
//import com.vd.canary.obmp.product.api.response.attribute.AttributeManagementDetailResp;
//import com.vd.canary.obmp.product.api.response.attribute.AttributeManagementResp;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.*;
//
///**
// * @Author WangRuilin
// * @Date 2020/4/10 13:55
// */
//public class AttributeManagement implements Function {
//
//    private static final Logger logger = LoggerFactory.getLogger(AttributeManagement.class);
//    @Autowired
//    private AttributeManagementFeign attributeManagementFeign;
//
//    @Override
//    public void performES(String msg) {
//        logger.info("AttributeManagement.msg"+msg);
//        ProductsRes productsRes = new ProductsRes();
//
////        ResponseBO<AttributeManagementDetailResp>  res = attributeManagementFeign.get(shopRes.getAttributeId());
////        AttributeManagementDetailResp pro = (AttributeManagementDetailResp)res.getData();
////        productsTO.setAttributeName(pro.getAttributeName());
////        productsTO.setAttributeName("颜色");
////
////        productsTO.setAttributeCode("11");
//
//
//    }
//
//}
