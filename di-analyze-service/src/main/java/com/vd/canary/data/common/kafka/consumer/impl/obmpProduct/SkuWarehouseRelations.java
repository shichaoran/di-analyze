package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer.StoreInfo;
import com.vd.canary.obmp.product.api.feign.SkuWarehouseRelationsFeign;
import com.vd.canary.obmp.product.api.response.warehouse.vo.SkuWarehouseRelationsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shichaoran
 * @Date 2020/4/10 15:40
 * @Version
 */
public class SkuWarehouseRelations implements Function {
    private static final Logger logger = LoggerFactory.getLogger(StoreInfo.class);
    @Autowired
    private SkuWarehouseRelationsFeign skuWarehouseRelationsFeign;
    @Override
    public void performES(String msg) {
        logger.info("StoreInfo.msg"+msg);
        ResponseBO<SkuWarehouseRelationsVO> res = skuWarehouseRelationsFeign.get("");
        SkuWarehouseRelationsVO skuWarehouseRelationsVO = (SkuWarehouseRelationsVO)res.getData();
        skuWarehouseRelationsVO.getWarehouseId();
        skuWarehouseRelationsVO.getWarehouseName();
        skuWarehouseRelationsVO.getInventory();
        skuWarehouseRelationsVO.getRegionalId();
        skuWarehouseRelationsVO.getRegionalName();
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        ProductsTO productsTO = new ProductsTO();
        productsTO.setWarehouseId(skuWarehouseRelationsVO.getWarehouseId());
        productsTO.setWarehouseName(skuWarehouseRelationsVO.getWarehouseName());
        productsTO.setInventory(skuWarehouseRelationsVO.getInventory().toString());
        productsTO.setRegionalName(skuWarehouseRelationsVO.getRegionalName());
        productsTO.setRegionalId(skuWarehouseRelationsVO.getRegionalId());
    }
    }

