package com.vd.canary.data.common.kafka.consumer.impl.ObmpProduct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.util.JSONUtils;
import com.vd.canary.obmp.product.api.feign.AttributeManagementFeign;
import com.vd.canary.obmp.product.api.feign.AttributeValueFeign;
import com.vd.canary.obmp.product.api.feign.BigDataApiFeign;
import com.vd.canary.obmp.product.api.feign.SkuAttributeRelationsFeign;
import com.vd.canary.obmp.product.api.request.category.foreground.CategoryRelationsReq;
import com.vd.canary.obmp.product.api.request.sku.SkuAttributeRelationsReq;
import com.vd.canary.obmp.product.api.response.attribute.AttributeManagementDetailResp;
import com.vd.canary.obmp.product.api.response.attribute.AttributeValueResp;
import com.vd.canary.obmp.product.api.response.brand.BrandManagementResp;
import com.vd.canary.obmp.product.api.response.category.CategoryRelationsResp;
import com.vd.canary.obmp.product.api.response.file.vo.FileManagementVO;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import org.apache.commons.lang3.StringUtils;
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
    private ProductESServiceImpl productESServiceImplTemp;

    @Autowired
    private BigDataApiFeign bigDataApiFeign;

    //@Autowired
    //private AttributeValueFeign attributeValueFeign;
    //@Autowired
    //private SkuAttributeRelationsFeign skuAttributeRelationsFeign;
    //@Autowired
    //private AttributeManagementFeign attributeManagementFeign;
    //@Autowired
    //ProductESServiceImpl productESService;

    @Override
    public void performES(String msg) {
        logger.info("SkuAttributeRelations.msg" + msg);
        if(StringUtils.isNotBlank(msg)){
            return;
        }
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        String type = (String) hashMap.get("type");
        String skuid = null;
        HashMap<String,Object> binlogMap = null;
        if(hashMap.containsKey("info")){
            binlogMap = JSON.parseObject(hashMap.get("info").toString(), HashMap.class);
        }
        ProductsTO productsTO = null;
        if (type.equals("insert") || type.equals("update")) {
            if(binlogMap != null && binlogMap.size() > 0){
                skuid = binlogMap.get("sku_id").toString();
                try {
                    Map<String, Object> esMap = productESServiceImplTemp.findById(skuid);
                    if(esMap != null){
                        Map<String, Object> resjson = reSetValue(esMap, binlogMap);
                        productESServiceImplTemp.updateProduct(resjson);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, Object> reSetValue(Map<String, Object> esMap,Map<String,Object> binlogMap){
        Set<Map.Entry<String, Object>> entries = binlogMap.entrySet();
        //for (Map.Entry<String, Object> entry : entries) {
        //    if (entry.getKey().equals("attribute_id")) {
        //        ResponseBO<AttributeManagementDetailResp> res = attributeManagementFeign.get(entry.getValue().toString());
        //        if(res != null){
        //            AttributeManagementDetailResp pro1 = (AttributeManagementDetailResp) res.getData();
        //            binlogMap.put("attributeName",pro1.getAttributeName());
        //            binlogMap.put("attributeType",pro1.getAttributeType());
        //
        //            Map<String, List<AttributeValueResp>> attributeMap = new HashMap<>();
        //            attributeMap.put(pro1.getAttributeName() + pro1.getAttributeType(), pro1.getAttributeValueList());
        //            binlogMap.put("attributeMap",attributeMap.toString());
        //        }
        //    }
        //    if (entry.getKey().equals("attribute_value_id")) {
        //        ResponseBO<?> res2 = attributeValueFeign.get(entry.getValue().toString());
        //        String pro2 = (String) res2.getData();
        //        binlogMap.put("value_Name",pro2);
        //    }
        //}
        //System.out.println("------------reSetValue.json:"+esMap);
        return esMap;
    }



}
