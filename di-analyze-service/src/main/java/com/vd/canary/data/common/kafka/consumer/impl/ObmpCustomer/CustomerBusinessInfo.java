package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.index.ShopTO;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;

import com.vd.canary.obmp.customer.api.feign.customer.CustomerClient;
import com.vd.canary.obmp.customer.api.request.customer.CustomerBaseBusinessReq;
import com.vd.canary.obmp.customer.api.response.customer.vo.CustomerBusinessInfoVO;

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
     * 多媒体地址->store_template_id->
     */
    @Autowired
    private CustomerClient customerClient;
    @Override
    public void performES(String msg) {
        logger.info("StoreInfo.msg"+msg);
        ResponseBO<CustomerBusinessInfoVO> res = customerClient.getBasicBusinessInfo(
              new CustomerBaseBusinessReq());
                CustomerBusinessInfoVO customerBusinessInfoVO = new CustomerBusinessInfoVO();

//        boothBusinessVO.getCustomerName();
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ShopTO shopVo = new ShopTO();
        shopVo.setBusinessBrand(customerBusinessInfoVO.getBusinessBrand());
        shopVo.setCustomerId( customerBusinessInfoVO.getCustomerId());
        shopVo.setBusinessArea(customerBusinessInfoVO.getBusinessArea());
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(msg, map.getClass());
        String goodsid=(String) map.get("goods_id");
        ShopESServiceImpl shopESService = new ShopESServiceImpl();

        if (goodsid.equals("insert")){
            shopESService.saveProduct(shopVo);
        }else if (goodsid.equals("delete")){
            shopESService.findById(shopVo.getId());
            shopESService.updateShop(shopVo);
            shopESService.saveProduct(shopVo);
        }else if (goodsid.equals("updata")){
            shopESService.deletedShopById(shopVo.getId());
        }

    }
}