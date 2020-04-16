package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;

import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;

import com.vd.canary.obmp.customer.api.feign.store.StoreMediaFeignClient;
import com.vd.canary.obmp.customer.api.response.customer.vo.store.StoreMediaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//import java.util.HashMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author shichaoran
 * @Date 2020/4/9 14:51
 * @Version
*/
public class StoreMedia implements Function {
    private static final Logger logger = LoggerFactory.getLogger(StoreInfo.class);


    /**
    * 多媒体地址->store_template_id->
     */
   @Autowired
    private StoreMediaFeignClient storeMediaFeignClient;
   @Override
   public void performES(String msg) {
        logger.info("StoreInfo.msg"+msg);
        ResponseBO<StoreMediaVO> res = storeMediaFeignClient.get("");
        StoreMediaVO storeMediaVO = (StoreMediaVO)res.getData();
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
       if (goodsid.equals("insert")) {
           try {
               shopESService.saveShop(shopTO);
           } catch (IOException e) {
               e.printStackTrace();
           }
       } else if (goodsid.equals("updata")) {
           try {
               shopESService.findById(shopTO.getId());
           } catch (IOException e) {
               e.printStackTrace();
           }
           try {
               shopESService.updateShop(shopTO);
           } catch (IOException e) {
               e.printStackTrace();
           }
       } else if (goodsid.equals("delete")) {
           try {
               shopESService.deletedShopById(shopTO.getId());
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
        }
   }

