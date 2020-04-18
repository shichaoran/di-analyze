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
    private ProductSpuFeign productSpuFeign;
    @Autowired
    private CategoryRelationsFeign categoryRelationsFeign;
    @Autowired
    private BrandManagementFeign brandManagementFeign;
    @Autowired
    private FileManagementFeign fileManagementFeign;
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
        HashMap<String,Object> hashMapSub = null;
        if(hashMap.containsKey("info")){
            hashMapSub = JSON.parseObject(hashMap.get("info").toString(), HashMap.class);
        }
        ProductsTO productsTO = null;
        if (type.equals("insert") ) {
            try {
                Map<String, Object> resMap = new HashMap();
                Map<String, Object> resjson = reSetValue(resMap, hashMapSub);
                productESServiceImplTemp.saveProduct(JSONObject.toJSONString(resjson),hashMapSub.get("id").toString());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else if(type.equals("update")){
            skuid = hashMapSub.get("id").toString();
            try {
                Map<String, Object> resMap = productESServiceImplTemp.findById(skuid);
                if(resMap != null){
                    Map<String, Object> resjson = reSetValue(resMap, hashMapSub);
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

    //public JSONObject reSetValue(JSONObject json,Map<String,Object> map){
    public Map<String, Object> reSetValue(Map<String, Object> json,Map<String,Object> map){
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (entry.getKey().equals("id")) json.put("skuId", entry.getValue());
            if (entry.getKey().equals("brand_id")) {
                json.put("proSkuBrandId", entry.getValue());
                //ResponseBO<BrandManagementResp> res = brandManagementFeign.brandDetail(entry.getValue().toString());
                //if(res != null){
                //    BrandManagementResp pro = (BrandManagementResp) res.getData();
                //    json.put("brandCode",pro.getBrandCode());
                //    json.put("bBrandName",pro.getBrandName());
                //    json.put("brandLoge",pro.getBrandLogo());
                //    json.put("brandShorthand",pro.getBrandShorthand());
                //    json.put("brandIntroduction",pro.getBrandIntroduction());
                //}
            }
            if (entry.getKey().equals("spu_id")) {
                json.put("proSkuSpuId", entry.getValue() );
                String value = entry.getValue().toString();
                //ResponseBO<ProductSpuDetailResp> res = productSpuFeign.spuDetail(entry.getValue().toString());
                //if(res != null){
                //    ProductSpuDetailResp pro = (ProductSpuDetailResp) res.getData();
                //    json.put("spuState",pro.getSpuState());
                //    json.put("proSpuSpuPic",JSONUtils.fromListByFastJson(pro.getSpuPic()));
                //    json.put("spuTitle",pro.getSpuTitle());
                //}
            }
            if (entry.getKey().equals("spu_code")) json.put("proSkuSpuCode", entry.getValue() );
            if (entry.getKey().equals("spu_name")) json.put("proSkuSpuName", entry.getValue() );
            if (entry.getKey().equals("sku_code")) json.put("proSkuSkuCode", entry.getValue() );
            if (entry.getKey().equals("sku_name")) json.put("proSkuSkuName", entry.getValue() );
            if (entry.getKey().equals("sku_title")) json.put("proSkuTitle", entry.getValue() );
            if (entry.getKey().equals("sku_sub_title")) json.put("proSkuSubTitle", entry.getValue() );
            if (entry.getKey().equals("three_category_id")) {
                json.put("threeCategoryId",entry.getValue());
                CategoryRelationsReq categoryRelationsReq = new CategoryRelationsReq();
                categoryRelationsReq.setBackgroundCategoryId(entry.getValue().toString());
                //ResponseBO<List<CategoryRelationsResp>> res = categoryRelationsFeign.listByCondition(categoryRelationsReq);
                //if(res != null){
                //    List<CategoryRelationsResp> pro = (List<CategoryRelationsResp>)res.getData();
                //    CategoryRelationsResp categoryRelationsResp = new CategoryRelationsResp();
                //    String[] foreCategoryFullCode = categoryRelationsResp.getForeCategoryFullCode().split("-");
                //    json.put("fOneCategoryCode",foreCategoryFullCode[0]);
                //    json.put("fTwoCategoryCode",foreCategoryFullCode[1]);
                //    json.put("fThreeCategoryCode",foreCategoryFullCode[2]);
                //
                //    String[] foreCategoryFullName = categoryRelationsResp.getForeCategoryFullName().split("-");
                //    json.put("fOneCategoryName",foreCategoryFullName[0]);
                //    json.put("fTwoCategoryName",foreCategoryFullName[1]);
                //    json.put("fThreeCategoryName",foreCategoryFullName[2]);
                //
                //    String[] foreCategoryFullId = categoryRelationsResp.getForegroundCategoryId().split("-");
                //    json.put("fOneCategoryId",foreCategoryFullId[0]);
                //    json.put("fTwoCategoryId",foreCategoryFullId[1]);
                //    json.put("fThreeCategoryId",foreCategoryFullId[2]);
                //}
            }
            if (entry.getKey().equals("three_category_code")) json.put("threeCategoryCode", entry.getValue() );
            if (entry.getKey().equals("three_category_name")) json.put("threeCategoryName", entry.getValue() );
            if (entry.getKey().equals("sku_supplier_id")) json.put("skuSupplierId", entry.getValue() );
            if (entry.getKey().equals("sku_supplier_name")) json.put("skuSupplierName", entry.getValue() );
            if (entry.getKey().equals("sku_state")) json.put("skuState", entry.getValue() );
            if (entry.getKey().equals("sku_pic")) {
                List<String> idList = JSONArray.parseArray(entry.getValue().toString(),String.class);
                //ResponseBO<List<FileManagementVO>> res = fileManagementFeign.listByIds(idList);
                //if(res != null){
                //    List<FileManagementVO> pros = (List<FileManagementVO>) res.getData();
                //    if(pros != null && pros.size() > 0){
                //        JSONArray jsonArray = new JSONArray();
                //        for(FileManagementVO file :pros){
                //            jsonArray.add(file);
                //        }
                //        json.put("proSkuSkuPicJson", jsonArray);
                //    }
                //}
            }
            if (entry.getKey().equals("sku_valuation_unit")) json.put("skuValuationUnit", entry.getValue() );
            if (entry.getKey().equals("sku_introduce")) json.put("skuIntroduce", entry.getValue() );
            if (entry.getKey().equals("gmt_create_time")) json.put("skuGmtCreateTime",entry.getValue());
            if (entry.getKey().equals("gmt_modify_time")) json.put("skuGmtModifyTime",entry.getValue());
            if (entry.getKey().equals("sku_auxiliary_unit")) json.put("skuAuxiliaryUnit", entry.getValue() );
        }
        System.out.println("------------reSetValue.json:"+json);
        return json;
    }

}
