package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.vo.ShopTo;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.customer.api.feign.store.StoreMediaFeignClient;
import com.vd.canary.obmp.customer.api.response.store.vo.StoreMediaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author shichaoran
 * @Date 2020/4/9 16:49
 * @Version
 */
public class CustomerBusinessInfo implements Function {
    private static final Logger logger = LoggerFactory.getLogger(StoreInfo.class);
    /**
     * 通过店铺->coustemer->展位编号
     */
    @Autowired
    private StoreMediaFeignClient storeMediaFeignClient;
    @Override
    public void performES(String msg) {
        ResponseBO<StoreMediaVO> res = storeMediaFeignClient.get("");
        StoreMediaVO storeMediaVO = (StoreMediaVO)res.getData();
        logger.info("BusinessCategorynfo.msg"+msg);
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ShopTo shopTo = new ShopTo();
        for (Map.Entry<String, String> entry : entries) {
            logger.info("key={},value={}" + entry.getKey(), entry.getValue());

            if (entry.getKey().equals("")
            ) {
                shopTo.setCustomerId(entry.getKey());
                shopTo.setBusinessArea(entry.getKey());
                shopTo.setBusinessCategory(entry.getKey());
                shopTo.setMainProducts(entry.getKey());
            }
        }
    }


}
