package com.vd.canary.data.common.kafka.consumer.impl.ObmpProduct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.data.util.JSONUtils;
import com.vd.canary.obmp.product.api.feign.BrandManagementFeign;
import com.vd.canary.obmp.product.api.feign.CategoryRelationsFeign;
import com.vd.canary.obmp.product.api.feign.FileManagementFeign;
import com.vd.canary.obmp.product.api.feign.ProductSpuFeign;

import lombok.extern.slf4j.Slf4j;
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
    //@Autowired
    //private ProductESServiceImpl productESServiceImpl;
    @Autowired
    private ProductSpuFeign roductSpuFeign;
    @Autowired
    private CategoryRelationsFeign categoryRelationsFeign;
    @Autowired
    private BrandManagementFeign brandManagementFeign;
    @Autowired
    private FileManagementFeign fileManagementFeign;
    @Override
    public void performES(String msg)  {
        logger.info("ProductSku.msg" + msg);
        if(StringUtils.isNotBlank(msg)){
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
        ProductESServiceImpl productESServiceImplTemp = new ProductESServiceImpl();
        ProductsTO productsTO = null;
        if (type.equals("insert") ) {
            productsTO = new ProductsTO();
            try {
                JSONObject json = JSONObject.parseObject(JSONUtils.beanToJson(productsTO));
                JSONObject resjson = reSetValue(json, hashMapSub);
                productESServiceImplTemp.saveProduct(resjson);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else if(type.equals("update")){
            /*if(hashMapSub != null) {
                Set<Map.Entry<String, Object>> entries = hashMapSub.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    if (entry.getKey().equals("spu_id")) skuid = entry.getValue().toString();
                }
            }*/
            skuid = hashMapSub.get("spu_id").toString();
            try {
                Map<String, Object> resMap = productESServiceImplTemp.findById(skuid);
                JSONObject resjson = reSetValue(JSONObject.parseObject(resMap.toString()), hashMapSub);
                productESServiceImplTemp.updateProduct(JSON.parseObject(resjson.toJSONString(), HashMap.class));
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

    public JSONObject reSetValue(JSONObject json,Map<String,Object> map){
        ProductsTO productsTO = null;
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (entry.getKey().equals("id")) json.put("skuId", entry.getValue());
            if (entry.getKey().equals("brand_id")) {
                json.put("proSkuBrandId", entry.getValue());
                /*ResponseBO<BrandManagementResp> res = brandManagementFeign.brandDetail(brandId);
                    BrandManagementResp pro = (BrandManagementResp) res.getData();
                    productsTO.setBrandCode(pro.getBrandCode());
                    productsTO.setBBrandName(pro.getBrandName());
                    productsTO.setBrandLoge(pro.getBrandLogo());
                    productsTO.setBrandShorthand(pro.getBrandShorthand());
                    productsTO.setBrandIntroduction(pro.getBrandIntroduction());*/
            }
            if (entry.getKey().equals("spu_id")) {
                json.put("proSkuSpuId", entry.getValue() );
               /*   ResponseBO<ProductSpuDetailResp> res = productspu.spuDetail(spuId);
                    ProductSpuDetailResp pro = (ProductSpuDetailResp) res.getData();
                    productsTO.setSpuState(pro.getSpuState());
                    productsTO.setProSpuSpuPic(pro.getSpuPic());
                    productsTO.setSpuTitle(pro.getSpuTitle());
                    productsTO.setSpuTitle(entry.getValue());*/
            }
            if (entry.getKey().equals("spu_code")) json.put("proSkuSpuCode", entry.getValue() );
            if (entry.getKey().equals("spu_name")) json.put("proSkuSpuName", entry.getValue() );
            if (entry.getKey().equals("sku_code")) json.put("proSkuSkuCode", entry.getValue() );
            if (entry.getKey().equals("sku_name")) json.put("proSkuSkuName", entry.getValue() );
            if (entry.getKey().equals("sku_title")) json.put("proSkuTitle", entry.getValue() );
            if (entry.getKey().equals("sku_sub_title")) json.put("proSkuSubTitle", entry.getValue() );
            if (entry.getKey().equals("three_category_id")) {
                    /*threeCategoryId = entry.getValue();
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
                    productsTO.setFThreeCategoryId(foreCategoryFullId[2]);*/
            }
            if (entry.getKey().equals("three_category_code")) json.put("threeCategoryCode", entry.getValue() );
            if (entry.getKey().equals("three_category_name")) json.put("threeCategoryName", entry.getValue() );
            if (entry.getKey().equals("sku_supplier_id")) json.put("skuSupplierId", entry.getValue() );
            if (entry.getKey().equals("sku_supplier_name")) json.put("skuSupplierName", entry.getValue() );
            if (entry.getKey().equals("sku_state")) json.put("skuState", entry.getValue() );
            if (entry.getKey().equals("sku_pic")) {
                    /*String skuPicId = entry.getValue();
                    ResponseBO<FileManagementVO> res = fileManagementFeign.get(skuPicId);
                    FileManagementVO pro = (FileManagementVO) res.getData();
                    productsTO.setType(pro.getType());
                    productsTO.setFileUrl(pro.getFileUrl());
                    productsTO.setFileSortNumber(pro.getSortNumber());
                    List list = new ArrayList();
                    list.add(pro.getType());
                    list.add(pro.getFileUrl());
                    list.add(pro.getSortNumber());
                    productsTO.setProSkuSkuPicJson(list.toString());*/
                json.put("proSkuSkuPicJson", entry.getValue() );
            }
            if (entry.getKey().equals("sku_valuation_unit")) json.put("skuValuationUnit", entry.getValue() );
            if (entry.getKey().equals("sku_introduce")) json.put("skuIntroduce", entry.getValue() );


            if (entry.getKey().equals("gmt_create_time")) {
                //productsTO.setSkuGmtCreateTime(LocalDateTime.parse(entry.getValue()));
            }
            if (entry.getKey().equals("gmt_modify_time")) {
                //productsTO.setSkuGmtModifyTime(LocalDateTime.parse(entry.getValue()));
            }
            if (entry.getKey().equals("sku_auxiliary_unit")) json.put("skuAuxiliaryUnit", entry.getValue() );
        }
        System.out.println("------------reSetValue.json:"+json);
        return json;
    }

}
