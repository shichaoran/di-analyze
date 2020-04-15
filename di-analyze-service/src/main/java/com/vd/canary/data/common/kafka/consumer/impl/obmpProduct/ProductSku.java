package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.product.api.feign.BackgroundCategoryFeign;
import com.vd.canary.obmp.product.api.feign.BrandManagementFeign;
import com.vd.canary.obmp.product.api.feign.CategoryRelationsFeign;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;
import com.vd.canary.obmp.product.api.request.category.foreground.CategoryRelationsReq;
import com.vd.canary.obmp.product.api.response.brand.BrandManagementResp;
import com.vd.canary.obmp.product.api.response.category.CategoryBackgroundResp;
import com.vd.canary.obmp.product.api.response.category.CategoryRelationsResp;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import com.vd.canary.obmp.product.api.response.warehouse.WarehouseManagementDetailResp;
import com.vd.canary.obmp.product.api.response.warehouse.vo.SkuWarehouseRelationsVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class ProductSku implements Function {
    private static final Logger logger = LoggerFactory.getLogger(ProductSku.class);
    @Autowired
    ProductESServiceImpl productESService;
    @Autowired
    private ProductSpuFeign productspu;
    @Autowired
    private CategoryRelationsFeign categoryRelationsFeign;
    @Autowired
    private BrandManagementFeign brandManagementFeign;

    @Override
    public void performES(String msg) {
        logger.info("ProductSku.msg" + msg);

        HashMap hashMap = JSON.parseObject(msg, HashMap.class);

        ProductsTO productsTO = new ProductsTO();
        String spuId = "";
        String threeCategoryId = "";
        String brandId = "";
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().equals("id")) productsTO.setSkuId(entry.getValue());
            if (entry.getKey().equals("brand_id")) {
                brandId = entry.getValue();
                productsTO.setProSkuBrandId(brandId);

                ResponseBO<BrandManagementResp> res = brandManagementFeign.brandDetail(brandId);
                BrandManagementResp pro = (BrandManagementResp) res.getData();
                productsTO.setBrandCode(pro.getBrandCode());
                productsTO.setBBrandName(pro.getBrandName());
                productsTO.setBrandLoge(pro.getBrandLogo());
                productsTO.setBrandShorthand(pro.getBrandShorthand());
                productsTO.setBrandIntroduction(pro.getBrandIntroduction());

            }
            if (entry.getKey().equals("spu_id")) {
                spuId = entry.getValue();
                productsTO.setProSkuSpuId(spuId);
                ResponseBO<ProductSpuDetailResp> res = productspu.spuDetail(spuId);
                ProductSpuDetailResp pro = (ProductSpuDetailResp) res.getData();
                productsTO.setSpuState(pro.getSpuState());
                productsTO.setProSpuSpuPic(pro.getSpuPic());
                productsTO.setSpuTitle(pro.getSpuTitle());
            }
            if (entry.getKey().equals("spu_code")) productsTO.setProSkuSpuCode(entry.getValue());
            if (entry.getKey().equals("spu_name")) productsTO.setProSkuSpuName(entry.getValue());
            if (entry.getKey().equals("sku_code")) productsTO.setProSkuSkuCode(entry.getValue());
            if (entry.getKey().equals("sku_name")) productsTO.setProSkuSkuName(entry.getValue());
            if (entry.getKey().equals("sku_title")) productsTO.setProSkuTitle(entry.getValue());
            if (entry.getKey().equals("sku_sub_title")) productsTO.setProSkuSubTitle(entry.getValue());
            if (entry.getKey().equals("three_category_id")) {
                threeCategoryId = entry.getValue();
                productsTO.setThreeCategoryId(threeCategoryId);
                CategoryRelationsReq categoryRelationsReq = new CategoryRelationsReq();
                categoryRelationsReq.setBackgroundCategoryId(threeCategoryId);
                ResponseBO<List<CategoryRelationsResp>> res = categoryRelationsFeign.listByCondition(categoryRelationsReq);
                List<CategoryRelationsResp> pro = (List<CategoryRelationsResp>)res.getData();
                CategoryRelationsResp categoryRelationsResp = new CategoryRelationsResp();
                String[] foreCategoryFullCode = categoryRelationsResp.getForeCategoryFullCode().split("-");
                productsTO.setFOneCategoryCode(foreCategoryFullCode[0]);
                productsTO.setFTwoCategoryCode(foreCategoryFullCode[1]);
                productsTO.setFThreeCategoryCode(foreCategoryFullCode[2]);

                String[] foreCategoryFullName = categoryRelationsResp.getForeCategoryFullName().split("-");
                productsTO.setFOneCategoryName(foreCategoryFullName[0]);
                productsTO.setFTwoCategoryName(foreCategoryFullName[1]);
                productsTO.setFThreeCategoryName(foreCategoryFullName[2]);

                String[] foreCategoryFullId = categoryRelationsResp.getForegroundCategoryId().split("-");
                productsTO.setFOneCategoryId(foreCategoryFullId[0]);
                productsTO.setFTwoCategoryId(foreCategoryFullId[1]);
                productsTO.setFThreeCategoryId(foreCategoryFullId[2]);

            }
            if (entry.getKey().equals("three_category_code")) productsTO.setThreeCategoryCode(entry.getValue());
            if (entry.getKey().equals("three_category_name")) productsTO.setThreeCategoryName(entry.getValue());
            if (entry.getKey().equals("sku_supplier_id")) productsTO.setSkuSupplierId(entry.getValue());
            if (entry.getKey().equals("sku_supplier_name")) productsTO.setSkuSupplierName(entry.getValue());
            if (entry.getKey().equals("sku_state")) productsTO.setSkuState(entry.getValue());
            if (entry.getKey().equals("sku_pic")) productsTO.setProSkuSkuPic(entry.getValue());
            if (entry.getKey().equals("sku_valuation_unit")) productsTO.setSkuValuationUnit(entry.getValue());
            if (entry.getKey().equals("sku_introduce")) productsTO.setSkuIntroduce(entry.getValue());

            if (entry.getKey().equals("gmt_create_time")) productsTO.setSkuGmtCreateTime(LocalDateTime.parse(entry.getValue()));
            if (entry.getKey().equals("gmt_modify_time")) productsTO.setSkuGmtModifyTime(LocalDateTime.parse(entry.getValue()));
            if (entry.getKey().equals("sku_auxiliary_unit")) productsTO.setSkuAuxiliaryUnit(entry.getValue());

            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<String, Object>();
            map = gson.fromJson(msg, map.getClass());
            String type = (String) map.get("type");

            if (type.equals("insert") || type.equals("update")) {
                try {
                    productESService.saveOrUpdateProduct(productsTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (type.equals("delete")) {
                try {
                    productESService.deletedProductById(productsTO.getSkuId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}