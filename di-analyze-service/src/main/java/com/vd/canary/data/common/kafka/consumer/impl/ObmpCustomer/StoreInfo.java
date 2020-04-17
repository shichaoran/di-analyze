package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.customer.api.feign.agreement.AgreementFeignClient;
import com.vd.canary.obmp.customer.api.feign.booth.BoothBusinessFeignClient;
import com.vd.canary.obmp.customer.api.feign.customer.CustomerClient;
import com.vd.canary.obmp.customer.api.feign.store.StoreLoopBannerFeignClient;
import com.vd.canary.obmp.customer.api.feign.store.StoreMediaFeignClient;
import com.vd.canary.obmp.product.api.feign.BrandManagementFeign;
import com.vd.canary.obmp.product.api.feign.CategoryRelationsFeign;
import com.vd.canary.obmp.product.api.feign.FileManagementFeign;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;
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

        HashMap hashMap = JSON.parseObject(msg, HashMap.class);

        ShopTO shopTO = new ShopTO();
        System.out.println("----------------------shopsTO000:" + JSONObject.toJSON(shopTO).toString());
        HashMap hashMapSub = null;
        if(hashMap.containsKey("info")){
            hashMapSub = JSON.parseObject(hashMap.get("info").toString(), HashMap.class);
        }
        if(hashMapSub != null){
            Set<Map.Entry<String, String>> entries = hashMapSub.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                System.out.println("----------------------entry:" + JSONObject.toJSON(entry).toString());

                if (entry.getKey().equals("id")) shopTO.setId(entry.getValue());
                if (entry.getKey().equals("name")) shopTO.setName(entry.getValue());
                if (entry.getKey().equals("customer_id")) shopTO.setCustomerId(entry.getValue());
            }
        }
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(msg, map.getClass());
        String type = (String) map.get("type");
        System.out.println("----------------------shopsTO:" + JSONObject.toJSON(shopTO).toString());
        ShopESServiceImpl shopESServiceImpl = new ShopESServiceImpl();

            if (type.equals("insert") || type.equals("update")) {
                try {
                    shopESServiceImpl.saveOrUpdateShop(shopTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (type.equals("delete")) {
                try {
                    shopESServiceImpl.deletedShopById(shopTO.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

//        public static void main(String[] args) throws IOException {
//            StoreInfo storeInfo = new StoreInfo();
//            String msg = "{\"id\":\"1\",\"name\":\"1\",\"customer_id\":\"1\"}";
//            ShopESServiceImpl shopESService = new ShopESServiceImpl();
//            ShopTO shopTO = new ShopTO();
//            shopTO.setId("1");
//            shopTO.setName("aa");
//            shopTO.setCustomerId("1");
//            shopESService.saveShop(shopTO);
//            System.out.println(shopTO.toString());
//
//    }
}
