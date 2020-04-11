/*
package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.BackgroundCategoryFeign;
import com.vd.canary.obmp.product.api.feign.BrandManagementFeign;
import com.vd.canary.obmp.product.api.request.category.background.CategoryBackgroundReq;
import com.vd.canary.obmp.product.api.response.brand.BrandManagementResp;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

*/
/**
 * @Author WangRuilin
 * @Date 2020/4/10 19:09
 *//*

public class BrandManagement implements Function {

    private static final Logger logger = LoggerFactory.getLogger(BrandManagement.class);
    @Autowired
    private BrandManagementFeign brandManagementFeign;
    @Override
    public void performES(String msg) {
        logger.info("BackgroundCategory.msg"+msg);

        ProductsTO productsTO = new ProductsTO();

        ResponseBO<BrandManagementResp> res = brandManagementFeign.brandDetail(productsTO.getProSkuBrandId());
        BrandManagementResp pro =  (BrandManagementResp)res.getData();
*/
/*
        productsTO.setBrandCode(pro.getBrandCode());
        productsTO.setBBrandName(pro.getBrandName());
        productsTO.setBrandLoge(pro.getBrandLogo());
        productsTO.setBrandShorthand(pro.getBrandShorthand());
        productsTO.setBrandIntroduction(pro.getBrandIntroduction());*//*


        productsTO.setBrandCode("222");
        productsTO.setBBrandName("钢材");
        productsTO.setBrandLoge("2");
        productsTO.setBrandShorthand("钢");
        productsTO.setBrandIntroduction("钢铁是什么");

    }
}
*/
