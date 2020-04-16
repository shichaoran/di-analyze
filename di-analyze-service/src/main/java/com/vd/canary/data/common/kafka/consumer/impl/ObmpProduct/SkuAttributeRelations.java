/*
package com.vd.canary.data.common.kafka.consumer.impl.ObmpProduct;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.AttributeManagementFeign;
import com.vd.canary.obmp.product.api.feign.AttributeValueFeign;
import com.vd.canary.obmp.product.api.feign.SkuAttributeRelationsFeign;
import com.vd.canary.obmp.product.api.request.sku.SkuAttributeRelationsReq;
import com.vd.canary.obmp.product.api.response.attribute.AttributeManagementDetailResp;
import com.vd.canary.obmp.product.api.response.attribute.AttributeValueResp;
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
public class SkuAttributeRelations implements Function {

    private static final Logger logger = LoggerFactory.getLogger(SkuAttributeRelations.class);
    @Autowired
    private AttributeValueFeign attributeValueFeign;
    @Autowired
    private SkuAttributeRelationsFeign skuAttributeRelationsFeign;
    @Autowired
    private AttributeManagementFeign attributeManagementFeign;
    @Autowired
    ProductESServiceImpl productESService;

    @Override
    public void performES(String msg) {
        logger.info("SkuAttributeRelations.msg" + msg);

        String skuId = "";
        String attributeId = "";
        String attributevalueId = "";
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("sku_id")) {
                skuId = entry.getValue();
                SkuAttributeRelationsReq skuAttributeRelationsReq = new SkuAttributeRelationsReq();
                skuAttributeRelationsReq.setSkuId(skuId);

                ResponseBO<List> res = skuAttributeRelationsFeign.queryBySkuId(skuAttributeRelationsReq);
                List pro = res.getData();
                try {
                    ProductsTO productsTO = productESService.findById(skuId);

                    productsTO.setAttributeId(pro.toString());

                    if (entry.getKey().equals("attribute_id")) {
                        attributeId = entry.getValue();
                        ResponseBO<AttributeManagementDetailResp> res1 = attributeManagementFeign.get(attributeId);
                        AttributeManagementDetailResp pro1 = (AttributeManagementDetailResp) res.getData();

                        productsTO.setAttributeName(pro1.getAttributeName());
                        productsTO.setAttributeType(pro1.getAttributeType());


                        Map<String, List<AttributeValueResp>> attributeMap = null;

                        attributeMap.put(pro1.getAttributeName() + pro1.getAttributeType(), pro1.getAttributeValueList());
                        productsTO.setAttributeMap(attributeMap.toString());

                    }

                    if (entry.getKey().equals("attribute_value_id")) {
                        attributevalueId = entry.getValue();

                        ResponseBO<?> res2 = attributeValueFeign.get(attributevalueId);
                        String pro2 = (String) res2.getData();
                        productsTO.setValue_Name(pro2);

                    }


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
