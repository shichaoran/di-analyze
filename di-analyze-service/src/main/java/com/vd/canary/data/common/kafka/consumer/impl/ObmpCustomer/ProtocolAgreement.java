//package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;
//
//import com.alibaba.fastjson.JSON;
//import com.vd.canary.core.bo.ResponseBO;
//import com.vd.canary.data.api.response.es.ShopRes;
//import com.vd.canary.data.api.response.es.vo.ShopVo;
//import com.vd.canary.data.common.es.index.ProductsTO;
//import com.vd.canary.data.common.kafka.consumer.impl.Function;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @Author shichaoran
// * @Date 2020/4/10 20:36
// * @Version
// */
//public class ProtocolAgreement implements Function {
//    private static final Logger logger = LoggerFactory.getLogger(StoreInfo.class);
//
//    @Override
//    public void performES(String msg) {
//        logger.info("StoreInfo.msg" + msg);
//        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
//        ProductsTO productsTO = new ProductsTO();
//        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
//        ShopRes shopRes = new ShopRes();
//        for (Map.Entry<String, String> entry : entries) {
//            if (entry.getKey().equals("")
//            ) {
//                logger.info("key={},value={}" + entry.getKey(), entry.getValue());
//                productsTO.getSkuId();
//            } else if (entry.getKey().equals("")) {
//                shopRes.setSkuID(productsTO.getSkuId());
//            } else if (entry.getKey().equals("")) {
//                shopRes.setSkuName(productsTO.getProSkuSkuName());
//
//            } else if (entry.getKey().equals("")) {
//                shopRes.setSkuName(productsTO.getProSkuSkuName());
//            } else if (entry.getKey().equals("")) {
//                shopRes.setSkupic(productsTO.getProSkuSkuPic());
//            } else if (entry.getKey().equals("")) {
//                shopRes.setSkuprice(productsTO.getSkuSellPriceJson());
//            } else if (entry.getKey().equals("")) {
//                shopRes.setSkuSubtitle(productsTO.getProSkuTitle());
//            } else if (entry.getKey().equals("")) {
//                shopRes.setUnit(productsTO.getSkuValuationUnit());
//            }
//        }
//
//    }
//}
