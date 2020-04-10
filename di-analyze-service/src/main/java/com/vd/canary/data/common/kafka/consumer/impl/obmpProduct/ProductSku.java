package com.vd.canary.data.common.kafka.consumer.impl.obmpProduct;

import com.alibaba.fastjson.JSON;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.common.es.index.ProductsTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author WangRuilin
 * @Date 2020/4/9 13:52
 */
public class ProductSku implements Function {

    private static final Logger logger = LoggerFactory.getLogger(ProductSku.class);
    @Override
    public void performES(String msg) {
        logger.info("ProductSku.msg"+msg);

        HashMap hashMap = JSON.parseObject(msg, HashMap.class);

        ProductsTO productsTO = new ProductsTO();

        Set<Map.Entry<String,String>> entries = hashMap.entrySet();
        for(Map.Entry<String,String> entry : entries){

            if(entry.getKey().equals("id")){

                productsTO.setSkuId(entry.getValue());
            }
            if(entry.getKey().equals("brand_id")){
                productsTO.setProSkuBrandId(entry.getValue());
            }
            if(entry.getKey().equals("spu_id")){
                productsTO.setProSkuSpuId(entry.getValue());
            }
            if(entry.getKey().equals("spu_code")){
                productsTO.setProSkuSpuCode(entry.getValue());
            }
            if(entry.getKey().equals("spu_name")){
                productsTO.setProSkuSpuName(entry.getValue());
            }
            if(entry.getKey().equals("sku_code")){
                productsTO.setProSkuSkuCode(entry.getValue());
            }
            if(entry.getKey().equals("sku_name")){
                productsTO.setProSkuSkuName(entry.getValue());
            }
            if(entry.getKey().equals("sku_title")){
                productsTO.setProSkuTitle(entry.getValue());
            }
            if(entry.getKey().equals("sku_sub_title")){
                productsTO.setProSkuSubTitle(entry.getValue());
            }
            if(entry.getKey().equals("three_category_id")){
                productsTO.setThreeCategoryId(entry.getValue());
            }
            if(entry.getKey().equals("three_category_code")){
                productsTO.setThreeCategoryCode(entry.getValue());
            }
            if(entry.getKey().equals("three_category_name")){
                productsTO.setThreeCategoryName(entry.getValue());
            }
            if(entry.getKey().equals("sku_supplier_id")){
                productsTO.setSkuSupplierId(entry.getValue());
            }
            if(entry.getKey().equals("sku_supplier_name")){
                productsTO.setSkuSupplierName(entry.getValue());
            }
            if(entry.getKey().equals("sku_state")){
                productsTO.setSkuState(entry.getValue());
            }
            if(entry.getKey().equals("sku_pic")) {
                productsTO.setProSkuSkuPic(entry.getValue());
            }
            if(entry.getKey().equals("sku_valuation_unit")){
                productsTO.setSkuValuationUnit(entry.getValue());
            }
            if(entry.getKey().equals("sku_introduce")){
                productsTO.setSkuIntroduce(entry.getValue());
            }

           /* if(entry.getKey().equals("gmt_create_time")){
                productsTO.setSkuGmtCreateTime(entry.getValue());
            }
            if(entry.getKey().equals("gmt_modify_time")){
                productsTO.setSkuGmtModifyTime(entry.getValue());
            }*/






        }

        // updateES(messages);

    }
}
