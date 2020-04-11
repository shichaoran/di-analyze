/*
package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;
import com.vd.canary.obmp.product.api.feign.WarehouseManagementFeign;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import com.vd.canary.obmp.product.api.response.warehouse.WarehouseManagementDetailResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

*/
/**
 * @Author WangRuilin
 * @Date 2020/4/11 10:02
 *//*

public class WarehouseManagement implements Function {
    private static final Logger logger = LoggerFactory.getLogger(WarehouseManagement.class);
    @Autowired
    private WarehouseManagementFeign warehouseManagementFeign;
    @Override
    public void performES(String msg) {
        logger.info("WarehouseManagement.msg"+msg);
        ProductsTO productsTO = new ProductsTO();

        ResponseBO<WarehouseManagementDetailResp> res = warehouseManagementFeign.get(productsTO.getSkuId());
        WarehouseManagementDetailResp pro =  (WarehouseManagementDetailResp)res.getData();

        productsTO.setWarehouseCode(pro.getWarehouseCode());
        productsTO.setWarehouseRegional(pro.getWarehouseRegional());
    }
}
*/
