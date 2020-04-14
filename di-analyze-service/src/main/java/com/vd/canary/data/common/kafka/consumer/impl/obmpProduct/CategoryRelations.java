/*
package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.CategoryRelationsFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CategoryRelations implements Function {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRelations.class);

    @Autowired
    ProductESServiceImpl productESService;
    @Autowired
    private CategoryRelationsFeign categoryRelationsFeign;
    @Override
    public void performES(String msg) {
        logger.info("CategoryRelations.msg"+msg);
        String backgroundCategoryId = "";
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("background_category_id")) {
                backgroundCategoryId = entry.getValue();
                ResponseBO<List<CategoryRelationsResp>> res = categoryRelationsFeign.listByBackCategoryId(backgroundCategoryId);
                List<CategoryRelationsResp> pro = (List<CategoryRelationsResp>) res.getData();
                try {
                    ProductsTO productsTO = productESService.findById(pro.skuId());

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
