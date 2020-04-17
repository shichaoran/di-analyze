package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.core.bo.ResponsePageVO;
import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;

import com.vd.canary.obmp.customer.api.feign.agreement.AgreementFeignClient;
import com.vd.canary.obmp.customer.api.response.agreement.AgreementInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ProtocolAgreement implements Function {
    private static final Logger logger = LoggerFactory.getLogger(ProtocolAgreement.class);

    @Autowired
    private AgreementFeignClient agreementFeignClient;

    @Override
    public void performES(String msg) {

        logger.info("ProtocolAgreement.msg" + msg);

        HashMap hashMap = JSON.parseObject(msg, HashMap.class);


//        ShopTO shopTO = new ShopTO();
//        System.out.println("----------------------shopsTO000:" + JSONObject.toJSON(shopTO).toString());
//        HashMap hashMapSub = null;
//        if(hashMap.containsKey("info")){
//            hashMapSub = JSON.parseObject(hashMap.get("info").toString(), HashMap.class);
//        }
//        if(hashMapSub != null){
//            Set<Map.Entry<String, String>> entries = hashMapSub.entrySet();
//            for (Map.Entry<String, String> entry : entries) {
//                System.out.println("----------------------entry:" + JSONObject.toJSON(entry).toString());
//
//                if (entry.getKey().equals("id")) shopTO.setId(entry.getValue());
//                if (entry.getKey().equals("name")) shopTO.setName(entry.getValue());
//                if (entry.getKey().equals("customer_id")) shopTO.setCustomerId(entry.getValue());
//
//            }
//        }






//        logger.info("BoothBusinessBoothCode.msg"+msg);
//        ResponseBO<AgreementInfoVO> res = agreementFeignClient.getAgreementInfo("");
//        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
//        AgreementInfoVO agreementInfoVO = (AgreementInfoVO)res.getData();
//        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
//        ShopTO shopTO = new ShopTO();
//        for (Map.Entry<String, String> entry : entries) {
//
//            shopTO.setBoothScheduledTime(agreementInfoVO.getSignData());
//
//        }
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
    }
}
