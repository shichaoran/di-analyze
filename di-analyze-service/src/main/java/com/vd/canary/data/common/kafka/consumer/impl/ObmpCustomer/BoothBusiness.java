//package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;
//
//import com.alibaba.fastjson.JSON;
//import com.google.gson.Gson;
//import com.vd.canary.core.bo.ResponseBO;
//import com.vd.canary.data.api.response.es.vo.ShopVo;
//import com.vd.canary.data.common.es.model.ShopTO;
//import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
//import com.vd.canary.data.common.kafka.consumer.impl.Function;
//import com.vd.canary.obmp.customer.api.feign.booth.BoothBusinessFeignClient;
//import com.vd.canary.obmp.customer.api.request.booth.BoothBusinessReq;
//import com.vd.canary.obmp.customer.api.response.booth.BoothBusinessInfoResp;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//import java.util.*;
//
///**
// * @Author shichaoran
// * @Date 2020/4/9 10:53
// * @Version
// */
//public class BoothBusiness implements Function {
//    private static final Logger logger = LoggerFactory.getLogger(BoothBusiness.class);
///**
// * 通过店铺->coustemer->展位编号
// */
//@Autowired
//    private BoothBusinessFeignClient boothBusinessFeignClient;
//
//
//    @Override
//    public void performES(String msg) {
//        logger.info("BoothBusinessBoothCode.msg"+msg);
//        ResponseBO<BoothBusinessInfoResp> res = boothBusinessFeignClient.queryBoothDetail(new BoothBusinessReq());
//        BoothBusinessInfoResp boothBusinessInfoResp = (BoothBusinessInfoResp)res.getData();
////        boothBusinessVO.getCustomerName();
//        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
//        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
//        List<String> list = new ArrayList<String>(Collections.singleton(boothBusinessInfoResp.getBoothCode()));
//
//        ShopTO shopTO = new ShopTO();
//        ShopVo shopVo = new ShopVo();
//        shopVo.setBoothCode(list);
//        shopVo.setCustomerId(boothBusinessInfoResp.getCustomerId());
//        Gson gson = new Gson();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map = gson.fromJson(msg, map.getClass());
//        String goodsid = (String) map.get("goods_id");
//        ShopESServiceImpl shopESService = new ShopESServiceImpl();
//        if (goodsid.equals("insert")) {
//            try {
//                shopESService.saveShop(shopTO);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (goodsid.equals("updata")) {
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
//        } else if (goodsid.equals("delete")) {
//            try {
//                shopESService.deletedShopById(shopTO.getId());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
