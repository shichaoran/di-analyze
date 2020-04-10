package com.vd.canary.data.common.kafka.consumer.impl.product;

import com.alibaba.fastjson.JSON;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.vo.ShopTo;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer.StoreInfo;
import com.vd.canary.obmp.customer.api.feign.store.StoreMediaFeignClient;
import com.vd.canary.obmp.customer.api.response.store.vo.StoreMediaVO;
import com.vd.canary.obmp.product.api.feign.SkuWarehouseRelationsFeign;
import com.vd.canary.obmp.product.api.response.warehouse.vo.SkuWarehouseRelationsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        ShopTo shopTo = new ShopTo();
        shopTo.setMediaUrl(skuWarehouseRelationsVO.getWarehouseId());
        shopTo.setStoreTemplateId(skuWarehouseRelationsVO.getWarehouseName());
    }
    }

