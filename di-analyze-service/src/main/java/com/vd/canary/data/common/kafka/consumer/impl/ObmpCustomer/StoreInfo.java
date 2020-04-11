package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.index.ShopTO;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    @Override
    public void performES(String msg) {
        logger.info("StoreInfo.msg"+msg);
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ShopTO shopTO = new ShopTO();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("")
            ){
                logger.info("key={},value={}" + entry.getKey(), entry.getValue());
            shopTO.setId(entry.getKey());
            shopTO.setCustomerId(entry.getKey());

            shopTO.setName(entry.getKey());
        }}
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(msg, map.getClass());
        String goodsid=(String) map.get("goods_id");
        ShopESServiceImpl shopESService = new ShopESServiceImpl();

        if (goodsid.equals("insert")){
            shopESService.saveProduct(shopTO);
        }else if (goodsid.equals("delete")){
            shopESService.findById(shopTO.getId());
            shopESService.updateShop(shopTO);
            shopESService.saveProduct(shopTO);
        }else if (goodsid.equals("updata")){
            shopESService.deletedShopById(shopTO.getId());
        }

    }
}
