package com.vd.canary.data.common.kafka.consumer.impl.ObmpProduct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.RegionalManagementFeign;
import com.vd.canary.obmp.product.api.feign.SkuWarehouseRelationsFeign;
import com.vd.canary.obmp.product.api.feign.WarehouseManagementFeign;
import com.vd.canary.obmp.product.api.response.region.RegionalManagementResp;
import com.vd.canary.obmp.product.api.response.warehouse.WarehouseManagementDetailResp;
import com.vd.canary.obmp.product.api.response.warehouse.vo.SkuWarehouseRelationsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkuWarehouseRelations implements Function {
    private static final Logger logger = LoggerFactory.getLogger(SkuWarehouseRelations.class);
    @Autowired
    private SkuWarehouseRelationsFeign skuWarehouseRelationsFeign;
    @Autowired
    ProductESServiceImpl productESService;
    @Autowired
    private RegionalManagementFeign regionalManagementFeign;
    @Autowired
    private WarehouseManagementFeign warehouseManagementFeign;

    @Override
    public void performES(String msg) {
        logger.info("SkuWarehouseRelations.msg" + msg);
        String skuId = "";
        String warehouseID = "";
        String regionalId = "";
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("sku_id")) {
                skuId = entry.getValue();

                ResponseBO<SkuWarehouseRelationsVO> res = skuWarehouseRelationsFeign.get(skuId);
                SkuWarehouseRelationsVO pro = (SkuWarehouseRelationsVO) res.getData();
                try {
                    ProductsTO productsTO = productESService.findById(skuId);

                    productsTO.setWarehouseId(pro.WAREHOUSE_ID);
                    productsTO.setWarehouseName(pro.WAREHOUSE_NAME);
                    productsTO.setInventory(pro.INVENTORY);
                    productsTO.setRegionalName(pro.REGIONAL_NAME);
                    productsTO.setRegionalId(pro.REGIONAL_ID);

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

                    if (entry.getKey().equals("warehouse_id")) {
                        warehouseID = entry.getValue();
                        ResponseBO<WarehouseManagementDetailResp> res1 = warehouseManagementFeign.get(warehouseID);

                        WarehouseManagementDetailResp pro1 = (WarehouseManagementDetailResp) res1.getData();
                        productsTO.setWarehouseCode(pro1.getWarehouseCode());
                        productsTO.setWarehouseRegional(pro1.getWarehouseRegional());


                    }

                    if (entry.getKey().equals("regional_id")) {
                        regionalId = entry.getValue();
                        ResponseBO<RegionalManagementResp> res2 = regionalManagementFeign.getRegionalManagement(regionalId);
                        RegionalManagementResp pro2 = (RegionalManagementResp) res2.getData();
                        productsTO.setWarehouseId(pro2.getRegionalCode());
                        productsTO.setWarehouseName(pro2.getRegionalName());
                        productsTO.setInventory(pro2.getRegionalScope());

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }

    }
}

