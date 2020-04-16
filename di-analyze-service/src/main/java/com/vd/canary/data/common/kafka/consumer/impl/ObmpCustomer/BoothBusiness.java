package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.customer.api.feign.booth.BoothBusinessFeignClient;
import com.vd.canary.obmp.customer.api.request.booth.BoothBusinessReq;
import com.vd.canary.obmp.customer.api.response.booth.BoothBusinessInfoResp;
import com.vd.canary.obmp.customer.api.response.booth.BoothBusinessResp;
import com.vd.canary.obmp.customer.api.response.booth.BoothBusinessVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @Author shichaoran
 * @Date 2020/4/9 10:53
 * @Version
 */
public class BoothBusiness implements Function {
    private static final Logger logger = LoggerFactory.getLogger(BoothBusiness.class);
/**
 * 通过店铺->coustemer->展位编号
 */
@Autowired
    private BoothBusinessFeignClient boothBusinessFeignClient;


    @Override
    public void performES(String msg) {
        logger.info("BoothBusinessBoothCode.msg"+msg);
        ResponseBO<BoothBusinessInfoResp> res = boothBusinessFeignClient.queryBoothDetail(new BoothBusinessReq());
        BoothBusinessInfoResp boothBusinessInfoResp = (BoothBusinessInfoResp)res.getData();
//        boothBusinessVO.getCustomerName();
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        List<String> list = new ArrayList<String>(Collections.singleton(boothBusinessInfoResp.getBoothCode()));


        ShopVo shopVo = new ShopVo();
        shopVo.setBoothCode(list);
        shopVo.setCustomerId(boothBusinessInfoResp.getCustomerId());
    }
}
