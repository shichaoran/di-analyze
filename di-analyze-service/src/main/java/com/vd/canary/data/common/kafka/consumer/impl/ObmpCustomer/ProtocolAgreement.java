package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.alibaba.fastjson.JSON;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.ShopProductRes;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;

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

    @Override
    public void performES(String msg) {
        logger.info("StoreInfo.msg" + msg);
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        ProductsTO productsTO = new ProductsTO();
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ShopRes shopRes = new ShopRes();
        ShopProductRes shopProductRes = new ShopProductRes();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("")
            ) {
                logger.info("key={},value={}" + entry.getKey(), entry.getValue());
                productsTO.getSkuId();
            } else if (entry.getKey().equals("")) {
                shopProductRes.setSkuId(productsTO.getSkuId());
            } else if (entry.getKey().equals("")) {
                shopProductRes.setSkuName(productsTO.getProSkuSkuName());

            } else if (entry.getKey().equals("")) {
                shopProductRes.setPriceType(productsTO.getProSkuSkuName());
            } else if (entry.getKey().equals("")) {
                shopProductRes.setSkuPic(productsTO.getProSkuSkuPic());
            } else if (entry.getKey().equals("")) {
                shopProductRes.setSkuPrice(productsTO.getSkuSellPriceJson());
            } else if (entry.getKey().equals("")) {
                shopProductRes.setSkuSubtitle(productsTO.getProSkuTitle());
            } else if (entry.getKey().equals("")) {
                shopProductRes.setUnit(productsTO.getSkuValuationUnit());
            }else if(entry.getKey().equals("")){
                shopProductRes.setSkuTitle(productsTO.getProSkuTitle());
            }
        }

    }
}
