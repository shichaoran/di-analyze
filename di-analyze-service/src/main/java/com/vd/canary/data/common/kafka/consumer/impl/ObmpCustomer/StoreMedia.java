package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.canal.KafkaService;
import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;

import com.vd.canary.obmp.customer.api.feign.store.StoreMediaFeignClient;
import com.vd.canary.obmp.customer.api.response.customer.vo.store.StoreMediaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//import java.util.HashMap;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Author shichaoran
 * @Date 2020/4/9 14:51
 * @Version
 */
public class StoreMedia implements Function {
    private static final Logger logger = LoggerFactory.getLogger(StoreMedia.class);
    private static Object ShopTO;


    /**
     * 多媒体地址->store_template_id->
     */
    @Autowired
    private StoreMediaFeignClient storeMediaFeignClient;

    @Override
    public void performES(String msg) {

        logger.info("StoreMedia.msg" + msg);
        ResponseBO<StoreMediaVO> res = storeMediaFeignClient.get("");
        StoreMediaVO storeMediaVO = (StoreMediaVO) res.getData();
        storeMediaVO.getMediaUrl();
        storeMediaVO.getStoreTemplateId();
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ShopVo shopVo = new ShopVo();
        shopVo.setMediaUrl(storeMediaVO.getMediaUrl());
        shopVo.setStoreTemplateId(storeMediaVO.getStoreTemplateId());
        ShopTO shopTO = new ShopTO();
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(msg, map.getClass());
        String goodsid = (String) map.get("goods_id");
        ShopESServiceImpl shopESService = new ShopESServiceImpl();
//        if (goodsid.equals("insert")) {
//            try {
//                shopESService.saveShop(shopTO);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } else if (goodsid.equals("updata")) {
//
//            JSONObject jsonObject = new JSONObject();
//            JSONObject jsonObject1 = jsonObject.getJSONObject(msg);
//            /**
//             * 最常见也是大多数情况下用的最多的，一般在键值对都需要使用
//             */
//            Map<String, String> maps = new HashMap<String, String>();
//
////            for (Map.Entry<String, Object> entry : jsonObject1.entrySet()) {
////                String mapKey = entry.getKey();
////                Object mapValue = (Object) entry.getValue();
////                if (mapKey.equals()) {
////
////                }
////            }
//        } else if (goodsid.equals("delete")) {
//            try {
//                shopESService.deletedShopById(shopTO.getId());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

}

