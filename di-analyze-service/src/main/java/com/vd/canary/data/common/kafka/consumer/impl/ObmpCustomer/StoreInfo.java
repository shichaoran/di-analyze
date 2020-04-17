package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

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
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ShopTO shopTO = new ShopTO();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("")
            ) {
                logger.info("key={},value={}" + entry.getKey(), entry.getValue());
                shopTO.setId(entry.getKey());
                shopTO.setCustomerId(entry.getKey());

                shopTO.setName(entry.getKey());
            }
        }
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
