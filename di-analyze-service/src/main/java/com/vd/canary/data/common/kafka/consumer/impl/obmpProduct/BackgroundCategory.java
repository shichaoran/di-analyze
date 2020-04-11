package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.common.es.index.ProductsTO;
import com.vd.canary.obmp.product.api.feign.BackgroundCategoryFeign;
import com.vd.canary.obmp.product.api.request.category.background.CategoryBackgroundReq;
import com.vd.canary.obmp.product.api.response.category.CategoryBackgroundResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author WangRuilin
 * @Date 2020/4/10 16:45
 */
public class BackgroundCategory implements Function {
    private static final Logger logger = LoggerFactory.getLogger(BackgroundCategory.class);
    @Autowired
    private BackgroundCategoryFeign backgroundCategoryFeign;
    @Override
    public void performES(String msg) {
        logger.info("BackgroundCategory.msg"+msg);

        ProductsTO productsTO = new ProductsTO();
        CategoryBackgroundReq categoryBackgroundReq = new CategoryBackgroundReq();

        categoryBackgroundReq.setCategoryCode(productsTO.getThreeCategoryCode());
        ResponseBO<CategoryBackgroundResp> res = backgroundCategoryFeign.getParent(categoryBackgroundReq);
        CategoryBackgroundResp pro =  (CategoryBackgroundResp)res.getData();
        if (pro.getParentId() != null) {
       /*     productsTO.setTwoCategoryCode(pro.getCategoryCode());
            productsTO.setTwoCategoryName(pro.getCategoryName());
            productsTO.setTwoCategoryId(pro.getParentId());

            categoryBackgroundReq.setCategoryCode(productsTO.getTwoCategoryCode());
            ResponseBO<CategoryBackgroundResp> res1 = backgroundCategoryFeign.getParent(categoryBackgroundReq);
            CategoryBackgroundResp pro1 = (CategoryBackgroundResp) res.getData();

            productsTO.setOneCategoryCode(pro1.getCategoryCode());
            productsTO.setOneCategoryName(pro1.getCategoryName());
            productsTO.setOneCategoryId(pro1.getParentId());*/

            productsTO.setTwoCategoryCode("11");
            productsTO.setTwoCategoryName("钢铁");
            productsTO.setTwoCategoryId("2");

            productsTO.setOneCategoryCode("12");
            productsTO.setOneCategoryName("木材");
            productsTO.setOneCategoryId("222");
        }

    }
}
