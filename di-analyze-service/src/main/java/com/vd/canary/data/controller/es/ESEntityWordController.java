package com.vd.canary.data.controller.es;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.sun.istack.NotNull;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.util.ResponseUtil;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.response.es.ESEntityWordRes;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.api.response.es.vo.ProductsDetailRes;
import com.vd.canary.data.common.es.helper.ESPageRes;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.service.es.ESEntityWordService;
import com.vd.canary.service.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@Component
public class ESEntityWordController extends BaseController {

    private ESEntityWordService esEntityWordService;

    @GetMapping("/entityword/get/{id}")
    public ResponseBO<ESEntityWordRes> get(@PathVariable("id") @NotNull String id){
        ESEntityWordRes esEntityWordRes =new ESEntityWordRes();
        esEntityWordRes.setKey("111");
        esEntityWordRes.setValue("测试");
         return ResponseUtil.ok(esEntityWordRes);
    }


//    @Autowired
//    private ProductESServiceImpl productESServiceImpl;
//
//    @PostMapping("/getindex")
//    public String getindex(@NotNull String id) throws Exception {
//        String res1 = productESServiceImpl.createIndex();
//        return res1;
//    }
//
//    @PostMapping("/getindex")
//    public String insertOne(@NotNull String data) throws Exception {
//        String res1 = productESServiceImpl.testInsertOne(data);
//        return res1;
//    }
//
//    @PostMapping("/search")
//    public ResponseBO<ProductsRes> getProductsByKey(@Valid ProductsReq productsReq) throws Exception {
//        ResponseBO<ProductsRes> res = new ResponseBO<ProductsRes>();
//        ProductsRes productsRes = new ProductsRes();
//        ESPageRes esPageRes = productESServiceImpl.boolQueryByKeyword(productsReq.getPageNum(), productsReq.getPageSize(), productsReq);
//        List<Map<String, Object>> recordList = esPageRes.getRecordList();
//        if (recordList != null && recordList.size() > 0) {
//            Map<String, String> categorys = new HashMap<>();//fThreeCategoryId:fThreeCategoryName
//            Map<String, String> brands = new HashMap<>(); //proSkuBrandId:bBrandName
//            Map<String, Map<String, String>> attributes = new HashMap<>(); //属性
//            List<ProductsDetailRes> productDetailResList = new ArrayList<>(); //商品详细列表
//            for (Map<String, Object> recordMap : recordList) {
//                ProductsDetailRes productsDetailRes = new ProductsDetailRes();
//                productsDetailRes.setSkuId(recordMap.get("skuId").toString());
//                productsDetailRes.setProSkuTitle(recordMap.containsKey("proSkuTitle")?recordMap.get("proSkuTitle").toString():"");
//                productsDetailRes.setProSkuSubTitle(recordMap.containsKey("proSkuSubTitle")?recordMap.get("proSkuSubTitle").toString():"");
//                productsDetailRes.setProSkuSkuPicJson(recordMap.containsKey("proSkuSkuPic")?recordMap.get("proSkuSkuPic").toString():"");
//                productsDetailRes.setAttributeMap(recordMap.containsKey("attributeMap")?recordMap.get("attributeMap").toString():"");
//                productsDetailRes.setSkuSellPriceJson(recordMap.containsKey("skuSellPriceJson")?recordMap.get("skuSellPriceJson").toString():"");
//                productsDetailRes.setSkuSellPriceType(recordMap.containsKey("skuSellPriceType")?Integer.parseInt(recordMap.get("skuSellPriceType").toString()):0);
////                productsDetailRes.setSkuGmtCreateTime(DateUtil.getDateTime(recordMap.containsKey("skuGmtCreateTime")?recordMap.get("skuGmtCreateTime").toString():""));
//                productsDetailRes.setShopId(recordMap.containsKey("storeId")?recordMap.get("storeId").toString():"");
//                productsDetailRes.setStoreInfoName(recordMap.containsKey("storeName")?recordMap.get("storeName").toString():"");
//                productsDetailRes.setBusinessCategory(recordMap.containsKey("businessCategory")?recordMap.get("businessCategory").toString():"");
//                productsDetailRes.setMainProducts(recordMap.containsKey("mainProducts")?recordMap.get("mainProducts").toString():"");
//                productsDetailRes.setBusinessArea(recordMap.containsKey("businessArea")?recordMap.get("businessArea").toString():"");
//                productsDetailRes.setBoothBusinessBoothCode(recordMap.containsKey("boothBusinessBoothCode")?recordMap.get("boothBusinessBoothCode").toString():"");
//                productsDetailRes.setCustomerProfilesLevel(recordMap.containsKey("customerProfilesLevel")?recordMap.get("customerProfilesLevel").toString():"");
//                productsDetailRes.setApproveState(recordMap.containsKey("approveState")?recordMap.get("approveState").toString():"");
//                productsDetailRes.setEnterpriseType(recordMap.containsKey("enterpriseType")?recordMap.get("enterpriseType").toString():"");
//                productsDetailRes.setStoreInfoStoreQrCode(recordMap.containsKey("storeInfoStoreQrCode")?recordMap.get("storeInfoStoreQrCode").toString():"");
////                productsDetailRes.setGmtCreateTime(DateUtil.getDateTime(recordMap.containsKey("gmtCreateTime")?recordMap.get("gmtCreateTime").toString():""));
//                productDetailResList.add(productsDetailRes);
//                categorys.put(recordMap.containsKey("fThreeCategoryCode")?recordMap.get("fThreeCategoryCode").toString():"", recordMap.containsKey("fThreeCategoryName")?recordMap.get("fThreeCategoryName").toString():"");
//                brands.put(recordMap.containsKey("proSkuBrandId")?recordMap.get("proSkuBrandId").toString():"", recordMap.containsKey("bBrandName")?recordMap.get("bBrandName").toString():"");
//                if (attributes.containsKey(recordMap.containsKey("attributeName")?recordMap.get("attributeName").toString():"")) {
//                    Map<String, String> mapt = attributes.get(recordMap.containsKey("attributeName") ? recordMap.get("attributeName").toString() : "");
//                    mapt.put(recordMap.containsKey("attributeValueId")?recordMap.get("attributeValueId").toString():"", recordMap.containsKey("value_Name")?recordMap.get("value_Name").toString():"");
//                } else {
//                    Map<String, String> mapt = new HashMap<>();
//                    mapt.put(recordMap.containsKey("attributeValueId")?recordMap.get("attributeValueId").toString():"", recordMap.containsKey("value_Name")?recordMap.get("value_Name").toString():"");
//                    attributes.put(recordMap.containsKey("attributeName")?recordMap.get("attributeName").toString():"", mapt);
//                }
//            }
//            productsRes.setCategorys(categorys);
//            productsRes.setBrands(brands);
//            productsRes.setAttributes(attributes);
//            productsRes.setProductDetailRes(productDetailResList);
//            productsRes.setTotal(esPageRes.getRecordCount());
//        }
//        res.setData(productsRes);
//        return res;
//    }


}
