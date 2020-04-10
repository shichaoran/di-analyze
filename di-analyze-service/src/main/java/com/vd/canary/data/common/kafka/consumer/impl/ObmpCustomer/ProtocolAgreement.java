package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.customer.api.feign.store.StoreMediaFeignClient;
import com.vd.canary.obmp.customer.api.response.store.vo.StoreMediaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author shichaoran
 * @Date 2020/4/10 20:36
 * @Version
 */
public class ProtocolAgreement implements Function {
    private static final Logger logger = LoggerFactory.getLogger(StoreInfo.class);
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
    }
}