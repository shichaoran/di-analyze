//package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;
//
//import com.alibaba.fastjson.JSON;
//import com.google.gson.Gson;
//import com.vd.canary.core.bo.ResponseBO;
//import com.vd.canary.data.common.es.model.ShopTO;
//import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
//import com.vd.canary.data.common.kafka.consumer.impl.Function;
//import com.vd.canary.obmp.customer.api.feign.customer.CustomerClient;
//import com.vd.canary.obmp.customer.api.request.customer.CustomerBaseBusinessReq;
//import com.vd.canary.obmp.customer.api.response.customer.vo.CustomerBusinessInfoVO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//import java.util.*;
//
///**
// * @Author shichaoran
// * @Date 2020/4/9 16:49
// * @Version
// */
//public class CustomerBusinessInfo implements Function {
//    private static final Logger logger = LoggerFactory.getLogger(StoreInfo.class);
//    /**
//     * 多媒体地址->store_template_id->
//     */
//    @Autowired
//    private CustomerClient customerClient;
//    @Override
//    public void performES(String msg) {logger.info("StoreInfo.msg"+msg);
//        ResponseBO<CustomerBusinessInfoVO> res = customerClient.getBasicBusinessInfo(new CustomerBaseBusinessReq());
//                CustomerBusinessInfoVO customerBusinessInfoVO = new CustomerBusinessInfoVO();
//
////        boothBusinessVO.getCustomerName();
//        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
//        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
//        ShopTO shopTO = new ShopTO();
//        List list = new ArrayList();
//        list.add(customerBusinessInfoVO.getBusinessBrand());
//        shopTO.setBusinessBrand(list);
//        shopTO.setCustomerId( customerBusinessInfoVO.getCustomerId());
//        shopTO.setBusinessArea(customerBusinessInfoVO.getBusinessArea());
//        Gson gson = new Gson();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map = gson.fromJson(msg, map.getClass());
//        String goodsid=(String) map.get("goods_id");
//        ShopESServiceImpl shopESService = new ShopESServiceImpl();
//
//        if (goodsid.equals("insert")){
//            try {
//                shopESService.saveShop(shopTO);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else if (goodsid.equals("delete")){
//            try {
//                shopESService.findById(shopTO.getId());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                shopESService.updateShop(shopTO);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }else if (goodsid.equals("updata")){
//            try {
//                shopESService.deletedShopById(shopTO.getId());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}