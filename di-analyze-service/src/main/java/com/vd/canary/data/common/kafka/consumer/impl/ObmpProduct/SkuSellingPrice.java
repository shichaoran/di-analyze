/*
package com.vd.canary.data.common.kafka.consumer.impl.ObmpProduct;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.SkuSellingPriceFeign;
import com.vd.canary.obmp.product.api.response.sku.vo.SkuSellingPriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class SkuSellingPrice implements Function {

    private static final Logger logger = LoggerFactory.getLogger(SkuSellingPrice.class);
    @Autowired
    private SkuSellingPriceFeign skuSellingPriceFeign;
    @Autowired
    ProductESServiceImpl productESService;

    @Override
    public void performES(String msg) {
        logger.info("SkuSellingPrice.msg" + msg);
        String skuId = "";
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("sku_id")) {
                skuId = entry.getValue();
                ResponseBO<SkuSellingPriceVO> res = skuSellingPriceFeign.get(skuId);
                SkuSellingPriceVO pro = (SkuSellingPriceVO) res.getData();

                try {
                    ProductsTO productsTO = productESService.findById(pro.getId());

                    productsTO.setSkuSellPriceJson(pro.getPriceJson().toString());
                    productsTO.setSkuSellPriceType(pro.getPriceType());

                    ProductESServiceImpl productESService = new ProductESServiceImpl();
                    Gson gson = new Gson();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map = gson.fromJson(msg, map.getClass());
                    String type = (String) map.get("type");
                    if (type.equals("update")) {
                        try {
                            productESService.saveOrUpdateProduct(productsTO);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
*/
