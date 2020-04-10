package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.api.response.es.vo.ShopTo;
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
        ShopTo shopvo = new ShopTo();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("")
            ){
                logger.info("key={},value={}" + entry.getKey(), entry.getValue());
            shopvo.setId(entry.getKey());
            shopvo.setCustomerId(entry.getKey());
            shopvo.setName(entry.getKey());
        }}
    }

    public void product(String optString, ShopRes res){

        if (optString.equals("insert")){

        }else if (optString.equals("delete")){

        }else if (optString.equals("updata")){

        }
    }
}
