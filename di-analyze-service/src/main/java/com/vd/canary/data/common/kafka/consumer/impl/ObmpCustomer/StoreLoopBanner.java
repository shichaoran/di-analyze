package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.model.ShopTO;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.customer.api.feign.store.StoreLoopBannerFeignClient;
import com.vd.canary.obmp.customer.api.request.customer.bo.store.StoreLoopBannerBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author shichaoran
 * @Date 2020/4/16 15:21
 * @Version
 */
public class StoreLoopBanner implements Function {
    private static final Logger logger = LoggerFactory.getLogger(BoothBusiness.class);
    @Autowired
    private StoreLoopBannerFeignClient storeLoopBannerFeignClient;
    @Override
    public void performES(String msg) {
        logger.info("BoothBusinessBoothCode.msg"+msg);
        ResponseBO create  = storeLoopBannerFeignClient.queryStoreLoopBanner("new StoreLoopBannerBO()");
        StoreLoopBannerBO storeLoopBannerBO = (StoreLoopBannerBO)create.getData();
        ShopVo shopVo = new ShopVo();
        shopVo.setImageName(storeLoopBannerBO.getImageName());
        shopVo.setImageUrl(storeLoopBannerBO.getImageUrl());
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
