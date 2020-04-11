/*
package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.alibaba.fastjson.JSON;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer.StoreInfo;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.obmp.product.api.feign.RegionalManagementFeign;
import com.vd.canary.obmp.product.api.response.region.RegionalManagementResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

*/
/**
 * @Author shichaoran
 * @Date 2020/4/10 16:12
 * @Version
 *//*

public class RegionalManagement implements Function {
    private static final Logger logger = LoggerFactory.getLogger(StoreInfo.class);
    @Autowired
    private RegionalManagementFeign regionalManagementFeign;
    @Override
    public void performES(String msg) {
        logger.info("RegionalManagement.msg"+msg);
        ResponseBO<RegionalManagementResp> res = regionalManagementFeign.getRegionalManagement("");
        RegionalManagementResp regionalManagementResp = (RegionalManagementResp)res.getData();
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ProductsTO productsTO = new ProductsTO();
        productsTO.setWarehouseId(regionalManagementResp.getRegionalCode());
        productsTO.setWarehouseName(regionalManagementResp.getRegionalName());
        productsTO.setInventory(regionalManagementResp.getRegionalScope());

    }
}
*/
