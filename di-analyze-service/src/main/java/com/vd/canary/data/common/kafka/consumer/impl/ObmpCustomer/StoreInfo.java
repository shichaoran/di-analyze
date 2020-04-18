package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.util.JSONUtils;
import com.vd.canary.obmp.customer.api.feign.agreement.AgreementFeignClient;
import com.vd.canary.obmp.customer.api.feign.booth.BoothBusinessFeignClient;
import com.vd.canary.obmp.customer.api.feign.customer.CustomerClient;
import com.vd.canary.obmp.customer.api.feign.store.StoreInfoFeignClient;
import com.vd.canary.obmp.customer.api.feign.store.StoreLoopBannerFeignClient;
import com.vd.canary.obmp.customer.api.feign.store.StoreMediaFeignClient;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cms.CMSAuthenticatedGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author shichaoran
 * @Date 2020/4/9 13:57
 * @Version
 */
@Component
public class StoreInfo implements Function {
    private static final Logger logger = LoggerFactory.getLogger(StoreInfo.class);

    /**
     * 通过店铺->coustemer->展位编号
     */
    @Autowired
    private ShopESServiceImpl shopESServiceImplTemp;
    @Autowired
    private StoreInfoFeignClient storeInfoFeignClient;
    @Autowired
    private BoothBusinessFeignClient boothBusinessFeignClient;
    @Autowired
    private CustomerClient customerClient;
    @Autowired
    private StoreLoopBannerFeignClient storeLoopBannerFeignClient;
    @Autowired
    private AgreementFeignClient agreementFeignClient;
    @Autowired
    private StoreMediaFeignClient storeMediaFeignClient;

    @Override
    public void performES(String msg) {
        logger.info("StoreInfo.msg" + msg);
        if (StringUtils.isEmpty(msg)) {
            return;
        }
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        String type = (String) hashMap.get("type");
        String id = "";

        HashMap hashMapSub = null;
        if (hashMap.containsKey("info")) {
            hashMapSub = JSON.parseObject(hashMap.get("info").toString(), HashMap.class);
        }

//        ShopESServiceImpl shopESServiceImplTemp = new ShopESServiceImpl();
        ShopTO shopTO = null;

        if (type.equals("insert")) {
            try {
                Map<String, Object> resMap = new HashMap();
                Map<String, Object> resjson = reSetValue(resMap, hashMapSub);
                shopESServiceImplTemp.saveShop(JSONObject.toJSONString(resjson),hashMapSub.get("id").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type.equals("update")) {

            id = hashMapSub.get("id").toString();
            try {
                Map<String, Object> resMap = shopESServiceImplTemp.findById(id);
                if (resMap != null) {
                    Map<String,Object> resjson = reSetValue(resMap, hashMapSub);
                    shopESServiceImplTemp.updateShop(resjson);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("delete")) {
            try {
                shopESServiceImplTemp.deletedShopById(shopTO.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Object> reSetValue(Map<String ,Object> json, Map<String,Object> map) {

        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (entry.getKey().equals("id")) json.put("id", entry.getValue());
            if (entry.getKey().equals("name")) json.put("name", entry.getValue());
            if (entry.getKey().equals("customer_id")) json.put("customerId", entry.getValue());


        }
        System.out.println("------------reSetValue.json:" + json);
        return json;
    }


}

