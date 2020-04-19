package com.vd.canary.data.common.kafka.consumer.impl.ObmpProduct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.util.JSONUtils;
import com.vd.canary.obmp.product.api.feign.BigDataApiFeign;
import com.vd.canary.obmp.product.api.feign.BrandManagementFeign;
import com.vd.canary.obmp.product.api.feign.CategoryRelationsFeign;
import com.vd.canary.obmp.product.api.feign.FileManagementFeign;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;

import com.vd.canary.obmp.product.api.request.category.foreground.CategoryRelationsReq;
import com.vd.canary.obmp.product.api.response.brand.BrandManagementResp;
import com.vd.canary.obmp.product.api.response.category.CategoryRelationsResp;
import com.vd.canary.obmp.product.api.response.file.vo.FileManagementVO;
import com.vd.canary.obmp.product.api.response.spu.ProductSpuDetailResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class ProductSku implements Function {
    private static final Logger logger = LoggerFactory.getLogger(ProductSku.class);
    @Autowired
    private ProductESServiceImpl productESServiceImplTemp;

    @Autowired
    private BigDataApiFeign bigDataApiFeign;

    @Override
    public void performES(String msg)  {
        logger.info("ProductSku.msg" + msg);
        if(StringUtils.isEmpty(msg)){
            return;
        }
        HashMap hashMap = JSON.parseObject(msg, HashMap.class);
        String type = (String) hashMap.get("type");
        String skuid = "";
        String spuId = "";
        String threeCategoryId = "";
        String brandId = "";
        HashMap<String,Object> bnglogMap = null;
        if(hashMap.containsKey("info")){
            bnglogMap = JSON.parseObject(hashMap.get("info").toString(), HashMap.class);
        }
        ProductsTO productsTO = null;
        if (type.equals("insert") ) {
            try {
                Map<String, Object> esMap = new HashMap();
                Map<String, Object> resjson = reSetValue(esMap, bnglogMap);
                productESServiceImplTemp.saveProduct(JSONObject.toJSONString(resjson),bnglogMap.get("id").toString());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else if(type.equals("update")){
            skuid = bnglogMap.get("id").toString();
            try {
                Map<String, Object> esMap = productESServiceImplTemp.findById(skuid);
                if(esMap != null){
                    Map<String, Object> resjson = reSetValue(esMap, bnglogMap);
                    productESServiceImplTemp.updateProduct(resjson);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("delete")){
            try {
                productESServiceImplTemp.deletedProductById(productsTO.getSkuId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //public JSONObject reSetValue(JSONObject esMap,Map<String,Object> binlogMap){
    public Map<String, Object> reSetValue(Map<String, Object> esMap,Map<String,Object> binlogMap){
        Set<Map.Entry<String, Object>> entries = binlogMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (entry.getKey().equals("id")) esMap.put("skuId", entry.getValue());
            if (entry.getKey().equals("brand_id")) {
                esMap.put("proSkuBrandId", entry.getValue());
                ResponseBO<BrandManagementResp> res = bigDataApiFeign.brandDetail(entry.getValue().toString());
                if(res != null){
                    BrandManagementResp pro = (BrandManagementResp) res.getData();
                    if(pro != null){
                        esMap.put("brandCode",pro.getBrandCode());
                        esMap.put("bBrandName",pro.getBrandName());
                        esMap.put("brandLoge",pro.getBrandLogo());
                        esMap.put("brandShorthand",pro.getBrandShorthand());
                        esMap.put("brandIntroduction",pro.getBrandIntroduction());
                    }
                }
            }
            if (entry.getKey().equals("spu_id")) {
                esMap.put("proSkuSpuId", entry.getValue() );
                String value = entry.getValue().toString();
                ResponseBO<ProductSpuDetailResp> res = bigDataApiFeign.spuDetail(entry.getValue().toString());
                if(res != null){
                    ProductSpuDetailResp pro = (ProductSpuDetailResp) res.getData();
                    if(pro != null){
                        esMap.put("spuState",pro.getSpuState());
                        esMap.put("proSpuSpuPic",JSONUtils.fromListByFastJson(pro.getSpuPic()));
                        esMap.put("spuTitle",pro.getSpuTitle());
                    }
                }
            }
            if (entry.getKey().equals("spu_code")) esMap.put("proSkuSpuCode", entry.getValue() );
            if (entry.getKey().equals("spu_name")) esMap.put("proSkuSpuName", entry.getValue() );
            if (entry.getKey().equals("sku_code")) esMap.put("proSkuSkuCode", entry.getValue() );
            if (entry.getKey().equals("sku_name")) esMap.put("proSkuSkuName", entry.getValue() );
            if (entry.getKey().equals("sku_title")) esMap.put("proSkuTitle", entry.getValue() );
            if (entry.getKey().equals("sku_sub_title")) esMap.put("proSkuSubTitle", entry.getValue() );
            if (entry.getKey().equals("three_category_id")) {
                esMap.put("threeCategoryId",entry.getValue());
                CategoryRelationsReq categoryRelationsReq = new CategoryRelationsReq();
                categoryRelationsReq.setBackgroundCategoryId(entry.getValue().toString());
                ResponseBO<List<CategoryRelationsResp>> res = bigDataApiFeign.listByCondition(categoryRelationsReq);
                if(res != null){
                    List<CategoryRelationsResp> pro = (List<CategoryRelationsResp>)res.getData();
                    if(pro != null && pro.size() > 0){
                        for(CategoryRelationsResp categoryRelationsResp :pro){
                            String[] foreCategoryFullCode = categoryRelationsResp.getForeCategoryFullCode().split("-");
                            esMap.put("fOneCategoryCode",foreCategoryFullCode[0]);
                            esMap.put("fTwoCategoryCode",foreCategoryFullCode[1]);
                            esMap.put("fThreeCategoryCode",foreCategoryFullCode[2]);

                            String[] foreCategoryFullName = categoryRelationsResp.getForeCategoryFullName().split("-");
                            esMap.put("fOneCategoryName",foreCategoryFullName[0]);
                            esMap.put("fTwoCategoryName",foreCategoryFullName[1]);
                            esMap.put("fThreeCategoryName",foreCategoryFullName[2]);

                            String[] foreCategoryFullId = categoryRelationsResp.getForegroundCategoryId().split("-");
                            esMap.put("fOneCategoryId",foreCategoryFullId[0]);
                            esMap.put("fTwoCategoryId",foreCategoryFullId[1]);
                            esMap.put("fThreeCategoryId",foreCategoryFullId[2]);
                        }
                    }
                }
            }
            if (entry.getKey().equals("three_category_code")) esMap.put("threeCategoryCode", entry.getValue() );
            if (entry.getKey().equals("three_category_name")) esMap.put("threeCategoryName", entry.getValue() );
            if (entry.getKey().equals("sku_supplier_id")) esMap.put("skuSupplierId", entry.getValue() );
            if (entry.getKey().equals("sku_supplier_name")) esMap.put("skuSupplierName", entry.getValue() );
            if (entry.getKey().equals("sku_state")) esMap.put("skuState", entry.getValue() );
            if (entry.getKey().equals("sku_pic")) {
                List<String> idList = JSONArray.parseArray(entry.getValue().toString(),String.class);
                ResponseBO<List<FileManagementVO>> res = bigDataApiFeign.listByIds(idList);
                if(res != null){
                    List<FileManagementVO> pros = (List<FileManagementVO>) res.getData();
                    if(pros != null && pros.size() > 0){
                        JSONArray jsonArray = new JSONArray();
                        for(FileManagementVO file :pros){
                            jsonArray.add(file);
                        }
                        esMap.put("proSkuSkuPicJson", jsonArray);
                    }
                }
            }
            if (entry.getKey().equals("sku_valuation_unit")) esMap.put("skuValuationUnit", entry.getValue() );
            if (entry.getKey().equals("sku_introduce")) esMap.put("skuIntroduce", entry.getValue() );
            if (entry.getKey().equals("gmt_create_time")) esMap.put("skuGmtCreateTime",entry.getValue());
            if (entry.getKey().equals("gmt_modify_time")) esMap.put("skuGmtModifyTime",entry.getValue());
            if (entry.getKey().equals("sku_auxiliary_unit")) esMap.put("skuAuxiliaryUnit", entry.getValue() );
        }
        System.out.println("------------reSetValue.json:"+esMap);
        return esMap;
    }

}
