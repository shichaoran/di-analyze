package com.vd.canary.data.common.kafka.consumer.impl.ObmpProduct;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.StoreProductRelationsFeign;
import com.vd.canary.obmp.product.api.response.store.vo.StoreProductRelationsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class StoreProductRelations implements Function {

    private static final Logger logger = LoggerFactory.getLogger(StoreProductRelations.class);
    @Autowired
    private StoreProductRelationsFeign storeProductRelationsFeign;
    @Autowired
    ProductESServiceImpl productESService;

    @Override
    public void performES(String msg) {

        logger.info("StoreProductRelations.msg" + msg);

        String skuId = "";
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("sku_id")) {
                skuId = entry.getValue();

                ResponseBO<StoreProductRelationsVO> res = storeProductRelationsFeign.get(skuId);
                StoreProductRelationsVO pro = (StoreProductRelationsVO) res.getData();
                try {
                    ProductsTO productsTO = productESService.findById(skuId);
                    productsTO.setStoreId(pro.getStoreId());
                    productsTO.setCategoryId(pro.getCategoryId());



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
