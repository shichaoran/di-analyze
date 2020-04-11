package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.BrandManagementFeign;
import com.vd.canary.obmp.product.api.feign.CategoryRelationsFeign;
import com.vd.canary.obmp.product.api.response.brand.BrandManagementResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author WangRuilin
 * @Date 2020/4/10 19:30
 */
public class CategoryRelations implements Function {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRelations.class);
    @Autowired
    private CategoryRelationsFeign categoryRelationsFeign;
    @Override
    public void performES(String msg) {
        logger.info("CategoryRelations.msg"+msg);

        ProductsTO productsTO = new ProductsTO();
//
//        ResponseBO<BrandManagementResp> res = categoryRelationsFeign.listByIds(productsTO.getThreeCategoryId());
//        BrandManagementResp pro =  (BrandManagementResp)res.getData();
//        productsTO.setBrandCode(pro.getBrandCode());




    }
}
