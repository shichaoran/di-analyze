package com.vd.canary.data.service.es.impl;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.CategoryReq;
import com.vd.canary.data.api.request.es.ProductDetailsReq;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.request.es.ThreeCategoryReq;
import com.vd.canary.data.api.response.es.CategoryRes;
import com.vd.canary.data.api.response.es.ProductDetailsRes;
import com.vd.canary.data.api.response.es.vo.CategoryVO;
import com.vd.canary.data.common.es.helper.ESPageRes;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.api.response.es.vo.ProductsDetailRes;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.service.es.ProductsService;
import com.vd.canary.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductESServiceImpl productESServiceImpl;

    @Override
    public ResponseBO<ProductsRes> getProductsByKey(@Valid ProductsReq productsReq) throws Exception {
        ResponseBO<ProductsRes> res = new ResponseBO<ProductsRes>();
        ProductsRes productsRes = new ProductsRes();
        ESPageRes esPageRes = productESServiceImpl.boolQueryByKeyword(productsReq.getPageNum(), productsReq.getPageSize(), productsReq);
        List<Map<String, Object>> recordList = esPageRes.getRecordList();
        if (recordList != null && recordList.size() > 0) {
            Map<String, String> categorys = new HashMap<>();//fThreeCategoryId:fThreeCategoryName
            Map<String, String> brands = new HashMap<>(); //proSkuBrandId:bBrandName
            Map<String, Map<String, String>> attributes = new HashMap<>(); //属性
            List<ProductsDetailRes> productDetailResList = new ArrayList<>(); //商品详细列表
            for (Map<String, Object> recordMap : recordList) {
                ProductsDetailRes productsDetailRes = new ProductsDetailRes();
                productsDetailRes.setSkuId(recordMap.get("skuId").toString());
                productsDetailRes.setProSkuTitle(recordMap.containsKey("proSkuTitle")?recordMap.get("proSkuTitle").toString():"");
                productsDetailRes.setProSkuSubTitle(recordMap.containsKey("proSkuSubTitle")?recordMap.get("proSkuSubTitle").toString():"");
                productsDetailRes.setProSkuSkuPicJson(recordMap.containsKey("proSkuSkuPic")?recordMap.get("proSkuSkuPic").toString():"");
                productsDetailRes.setAttributeMap(recordMap.containsKey("attributeMap")?recordMap.get("attributeMap").toString():"");
                productsDetailRes.setSkuSellPriceJson(recordMap.containsKey("skuSellPriceJson")?recordMap.get("skuSellPriceJson").toString():"");
                productsDetailRes.setSkuSellPriceType(recordMap.containsKey("skuSellPriceType")?Integer.parseInt(recordMap.get("skuSellPriceType").toString()):0);
//                productsDetailRes.setSkuGmtCreateTime(DateUtil.getDateTime(recordMap.containsKey("skuGmtCreateTime")?recordMap.get("skuGmtCreateTime").toString():""));
                productsDetailRes.setShopId(recordMap.containsKey("storeId")?recordMap.get("storeId").toString():"");
                productsDetailRes.setStoreInfoName(recordMap.containsKey("storeName")?recordMap.get("storeName").toString():"");
                productsDetailRes.setBusinessCategory(recordMap.containsKey("businessCategory")?recordMap.get("businessCategory").toString():"");
                productsDetailRes.setMainProducts(recordMap.containsKey("mainProducts")?recordMap.get("mainProducts").toString():"");
                productsDetailRes.setBusinessArea(recordMap.containsKey("businessArea")?recordMap.get("businessArea").toString():"");
                productsDetailRes.setBoothBusinessBoothCode(recordMap.containsKey("boothBusinessBoothCode")?recordMap.get("boothBusinessBoothCode").toString():"");
                productsDetailRes.setCustomerProfilesLevel(recordMap.containsKey("customerProfilesLevel")?recordMap.get("customerProfilesLevel").toString():"");
                productsDetailRes.setApproveState(recordMap.containsKey("approveState")?recordMap.get("approveState").toString():"");
                productsDetailRes.setEnterpriseType(recordMap.containsKey("enterpriseType")?recordMap.get("enterpriseType").toString():"");
                productsDetailRes.setStoreInfoStoreQrCode(recordMap.containsKey("storeInfoStoreQrCode")?recordMap.get("storeInfoStoreQrCode").toString():"");
//                productsDetailRes.setGmtCreateTime(DateUtil.getDateTime(recordMap.containsKey("gmtCreateTime")?recordMap.get("gmtCreateTime").toString():""));
                productDetailResList.add(productsDetailRes);
                categorys.put(recordMap.containsKey("fThreeCategoryCode")?recordMap.get("fThreeCategoryCode").toString():"", recordMap.containsKey("fThreeCategoryName")?recordMap.get("fThreeCategoryName").toString():"");
                brands.put(recordMap.containsKey("proSkuBrandId")?recordMap.get("proSkuBrandId").toString():"", recordMap.containsKey("bBrandName")?recordMap.get("bBrandName").toString():"");
                if (attributes.containsKey(recordMap.containsKey("attributeName")?recordMap.get("attributeName").toString():"")) {
                    Map<String, String> mapt = attributes.get(recordMap.containsKey("attributeName")?recordMap.get("attributeName").toString():"");
                    mapt.put(recordMap.containsKey("attributeValueId")?recordMap.get("attributeValueId").toString():"", recordMap.containsKey("value_Name")?recordMap.get("value_Name").toString():"");
                } else {
                    Map<String, String> mapt = new HashMap<>();
                    mapt.put(recordMap.containsKey("attributeValueId")?recordMap.get("attributeValueId").toString():"", recordMap.containsKey("value_Name")?recordMap.get("value_Name").toString():"");
                    attributes.put(recordMap.containsKey("attributeName")?recordMap.get("attributeName").toString():"", mapt);
                }
            }
            productsRes.setCategorys(categorys);
            productsRes.setBrands(brands);
            productsRes.setAttributes(attributes);
            productsRes.setProductDetailRes(productDetailResList);
            productsRes.setTotal(esPageRes.getRecordCount());
        }
        res.setData(productsRes);
        return res;
    }

    @Override
    public ResponseBO<ProductsRes> getProductByCategory(@Valid ThreeCategoryReq threeCategoryReq) throws Exception {
        ResponseBO<ProductsRes> res = new ResponseBO<ProductsRes>();
        ProductsRes productsRes = new ProductsRes();
        ESPageRes esPageRes = productESServiceImpl.boolQueryByKeyword(threeCategoryReq.getPageNum(), threeCategoryReq.getPageSize(), new ProductsReq());
        List<Map<String, Object>> recordList = esPageRes.getRecordList();
        if (recordList != null && recordList.size() > 0) {
            Map<String, String> categorys = new HashMap<>();//fThreeCategoryId:fThreeCategoryName
            Map<String, String> brands = new HashMap<>(); //proSkuBrandId:bBrandName
            Map<String, Map<String, String>> attributes = new HashMap<>(); //属性
            List<ProductsDetailRes> productDetailResList = new ArrayList<>(); //商品详细列表
            for (Map<String, Object> recordMap : recordList) {
                ProductsDetailRes productsDetailRes = new ProductsDetailRes();
                productsDetailRes.setSkuId(recordMap.containsKey("skuId")?recordMap.get("skuId").toString():"");
                productsDetailRes.setProSkuTitle(recordMap.containsKey("proSkuTitle")?recordMap.get("proSkuTitle").toString():"");
                productsDetailRes.setProSkuSubTitle(recordMap.containsKey("proSkuSubTitle")?recordMap.get("proSkuSubTitle").toString():"");
                productsDetailRes.setProSkuSkuPicJson(recordMap.containsKey("proSkuSkuPic")?recordMap.get("proSkuSkuPic").toString():"");
                productsDetailRes.setAttributeMap(recordMap.containsKey("attributeMap")?recordMap.get("attributeMap").toString():"");
                productsDetailRes.setSkuSellPriceJson(recordMap.containsKey("skuSellPriceJson")?recordMap.get("skuSellPriceJson").toString():"");
                productsDetailRes.setSkuSellPriceType(recordMap.containsKey("skuSellPriceType")?Integer.parseInt(recordMap.get("skuSellPriceType").toString()):0);
//                productsDetailRes.setSkuGmtCreateTime(DateUtil.getDateTime(recordMap.containsKey("skuGmtCreateTime")?recordMap.get("skuGmtCreateTime").toString():""));
                productsDetailRes.setShopId(recordMap.containsKey("storeId")?recordMap.get("storeId").toString():"");
                productsDetailRes.setStoreInfoName(recordMap.containsKey("storeName")?recordMap.get("storeName").toString():"");
                productsDetailRes.setBusinessCategory(recordMap.containsKey("businessCategory")?recordMap.get("businessCategory").toString():"");
                productsDetailRes.setMainProducts(recordMap.containsKey("mainProducts")?recordMap.get("mainProducts").toString():"");
                productsDetailRes.setBusinessArea(recordMap.containsKey("businessArea")?recordMap.get("businessArea").toString():"");
                productsDetailRes.setBoothBusinessBoothCode(recordMap.containsKey("boothBusinessBoothCode")?recordMap.get("boothBusinessBoothCode").toString():"");
                productsDetailRes.setCustomerProfilesLevel(recordMap.containsKey("customerProfilesLevel")?recordMap.get("customerProfilesLevel").toString():"");
                productsDetailRes.setApproveState(recordMap.containsKey("approveState")?recordMap.get("approveState").toString():"");
                productsDetailRes.setEnterpriseType(recordMap.containsKey("enterpriseType")?recordMap.get("enterpriseType").toString():"");
                productsDetailRes.setStoreInfoStoreQrCode(recordMap.containsKey("storeInfoStoreQrCode")?recordMap.get("storeInfoStoreQrCode").toString():"");
//                productsDetailRes.setGmtCreateTime(DateUtil.getDateTime(recordMap.containsKey("gmtCreateTime")?recordMap.get("gmtCreateTime").toString():""));
                productDetailResList.add(productsDetailRes);
                categorys.put(recordMap.containsKey("fThreeCategoryCode")?recordMap.get("fThreeCategoryCode").toString():"", recordMap.containsKey("fThreeCategoryName")?recordMap.get("fThreeCategoryName").toString():"");
                brands.put(recordMap.containsKey("proSkuBrandId")?recordMap.get("proSkuBrandId").toString():"", recordMap.containsKey("bBrandName")?recordMap.get("bBrandName").toString():"");
                if (attributes.containsKey(recordMap.containsKey("attributeName")?recordMap.get("attributeName").toString():"")) {
                    Map<String, String> mapt = attributes.get(recordMap.containsKey("attributeName")?recordMap.get("attributeName").toString():"");
                    mapt.put(recordMap.containsKey("attributeValueId")?recordMap.get("attributeValueId").toString():"", recordMap.containsKey("value_Name")?recordMap.get("value_Name").toString():"");
                } else {
                    Map<String, String> mapt = new HashMap<>();
                    mapt.put(recordMap.containsKey("attributeValueId")?recordMap.get("attributeValueId").toString():"", recordMap.containsKey("value_Name")?recordMap.get("value_Name").toString():"");
                    attributes.put(recordMap.containsKey("attributeName")?recordMap.get("attributeName").toString():"", mapt);
                }
            }
            productsRes.setCategorys(categorys);
            productsRes.setBrands(brands);
            productsRes.setAttributes(attributes);
            productsRes.setProductDetailRes(productDetailResList);
            productsRes.setTotal(esPageRes.getRecordCount());
        }
        res.setData(productsRes);
        return res;
    }


    @Override
    public ResponseBO<ProductDetailsRes> getProductsDetail(@Valid ProductDetailsReq productDetailsReq) throws IOException {
        ResponseBO<ProductDetailsRes> res = new ResponseBO<ProductDetailsRes>();
        ProductDetailsRes productDetailsRes = new ProductDetailsRes();
        Map<String, Object> maps = productESServiceImpl.findById(productDetailsReq.getProductId());
        if (maps != null && maps.size() > 0) {
            for (Map.Entry<String, Object> map : maps.entrySet()) {

                productDetailsRes.setSkuTitle(map.getKey().equals("skuTitle") ? map.getValue().toString() : "");
                productDetailsRes.setSkuSubTitle(map.getKey().equals("skuSubTitle") ? map.getValue().toString() : "");
                productDetailsRes.setPriceJson(map.getKey().equals("attributeMap") ? map.getValue().toString() : "");
                productDetailsRes.setPriceType(map.getKey().equals("priceJson") ? Integer.parseInt(map.getValue().toString()) : 0);
                productDetailsRes.setSkuIntroduce(map.getKey().equals("skuIntroduce") ? map.getValue().toString() : "");
                productDetailsRes.setProSkuSkuPicJson(map.getKey().equals("proSkuSkuPicJson") ? map.getValue().toString() : "");
                productDetailsRes.setRegionalId(map.getKey().equals("regionalId") ? map.getValue().toString() : "");
                productDetailsRes.setRegionalName(map.getKey().equals("regionalName") ? map.getValue().toString() : "");

                Map<String, Map<String, String>> attributes = new HashMap<>(); //属性

                productDetailsRes.setAttributeMap(attributes);

            }
        }
        res.setData(productDetailsRes);
        return res;
    }

    @Override
    public ResponseBO<CategoryRes> categoryRes(@Valid CategoryReq categoryReq) {


        ResponseBO<CategoryRes> res = new ResponseBO<CategoryRes>();
        CategoryRes categoryRes = new CategoryRes();
        CategoryVO categoryVO = new CategoryVO();
        Map map = new HashMap();

        List<Map<String, Object>> list = null ; //productESServiceImpl.xxx(categoryRes.getSkuId());

//        try {
//            ProductsTO productsTO = productESServiceImpl.findById(categoryRes.getSkuId());
//
//            categoryRes.setSkuId(productsTO.getSkuId());
//
//            categoryVO.setFOneCategoryId(productsTO.getFOneCategoryId());
//            categoryVO.setFOneCategoryCode(productsTO.getFOneCategoryCode());
//            categoryVO.setFOneCategoryName(productsTO.getFOneCategoryName());
//            categoryVO.setFTwoCategoryId(productsTO.getFTwoCategoryId());
//            categoryVO.setFTwoCategoryCode(productsTO.getFTwoCategoryCode());
//            categoryVO.setFTwoCategoryName(productsTO.getFTwoCategoryName());
//            categoryVO.setFThreeCategoryId(productsTO.getFThreeCategoryId());
//            categoryVO.setFThreeCategoryCode(productsTO.getFThreeCategoryCode());
//            categoryVO.setFThreeCategoryName(productsTO.getFThreeCategoryName());
//
//            categoryRes.setCategoryVO(categoryVO);
//            map.put(productsTO.getSkuId(),categoryVO);
//            categoryRes.setMaplist(map);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return res;
    }
}
