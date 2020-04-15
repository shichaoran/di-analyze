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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;


@Slf4j
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductESServiceImpl productESServiceImpl;

    @Override
    public ResponseBO<ProductsRes> getProductsByKey(@Valid ProductsReq productsReq) {
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
                productsDetailRes.setProSkuTitle(recordMap.get("proSkuTitle").toString());
                productsDetailRes.setProSkuSubTitle(recordMap.get("proSkuSubTitle").toString());
                productsDetailRes.setProSkuSkuPic(recordMap.get("proSkuSkuPic").toString());
                productsDetailRes.setAttributeMap(recordMap.get("attributeMap").toString());
                productsDetailRes.setSkuSellPriceJson(recordMap.get("skuSellPriceJson").toString());
                productsDetailRes.setSkuSellPriceType(Integer.parseInt(recordMap.get("skuSellPriceType").toString()));
                productsDetailRes.setSkuGmtCreateTime(recordMap.get("skuGmtCreateTime").toString());
                productsDetailRes.setShopId(recordMap.get("storeId").toString());
                productsDetailRes.setStoreInfoName(recordMap.get("storeName").toString());
                productsDetailRes.setBusinessCategory(recordMap.get("businessCategory").toString());
                productsDetailRes.setMainProducts(recordMap.get("mainProducts").toString());
                productsDetailRes.setBusinessArea(recordMap.get("businessArea").toString());
                productsDetailRes.setBoothBusinessBoothCode(recordMap.get("boothBusinessBoothCode").toString());
                productsDetailRes.setCustomerProfilesLevel(recordMap.get("customerProfilesLevel").toString());
                productsDetailRes.setApproveState(recordMap.get("approveState").toString());
                productsDetailRes.setEnterpriseType(recordMap.get("enterpriseType").toString());
                productsDetailRes.setStoreInfoStoreQrCode(recordMap.get("storeInfoStoreQrCode").toString());
                productsDetailRes.setGmtCreateTime(recordMap.get("gmtCreateTime").toString());
                productDetailResList.add(productsDetailRes);
                categorys.put(recordMap.get("fThreeCategoryCode").toString(), recordMap.get("fThreeCategoryName").toString());
                brands.put(recordMap.get("proSkuBrandId").toString(), recordMap.get("bBrandName").toString());
                if (attributes.containsKey(recordMap.get("attributeName").toString())) {
                    Map<String, String> mapt = attributes.get(recordMap.get("attributeName").toString());
                    mapt.put(recordMap.get("attributeValueId").toString(), recordMap.get("value_Name").toString());
                } else {
                    Map<String, String> mapt = new HashMap<>();
                    mapt.put(recordMap.get("attributeValueId").toString(), recordMap.get("value_Name").toString());
                    attributes.put(recordMap.get("attributeName").toString(), mapt);
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
    public ResponseBO<ProductsRes> getProductByCategory(@Valid ThreeCategoryReq threeCategoryReq) {
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
                productsDetailRes.setSkuId(recordMap.get("skuId").toString());
                productsDetailRes.setProSkuTitle(recordMap.get("proSkuTitle").toString());
                productsDetailRes.setProSkuSubTitle(recordMap.get("proSkuSubTitle").toString());
                productsDetailRes.setProSkuSkuPic(recordMap.get("proSkuSkuPic").toString());
                productsDetailRes.setAttributeMap(recordMap.get("attributeMap").toString());
                productsDetailRes.setSkuSellPriceJson(recordMap.get("skuSellPriceJson").toString());
                productsDetailRes.setSkuSellPriceType(Integer.parseInt(recordMap.get("skuSellPriceType").toString()));
                productsDetailRes.setSkuGmtCreateTime(recordMap.get("skuGmtCreateTime").toString());
                productsDetailRes.setShopId(recordMap.get("storeId").toString());
                productsDetailRes.setStoreInfoName(recordMap.get("storeName").toString());
                productsDetailRes.setBusinessCategory(recordMap.get("businessCategory").toString());
                productsDetailRes.setMainProducts(recordMap.get("mainProducts").toString());
                productsDetailRes.setBusinessArea(recordMap.get("businessArea").toString());
                productsDetailRes.setBoothBusinessBoothCode(recordMap.get("boothBusinessBoothCode").toString());
                productsDetailRes.setCustomerProfilesLevel(recordMap.get("customerProfilesLevel").toString());
                productsDetailRes.setApproveState(recordMap.get("approveState").toString());
                productsDetailRes.setEnterpriseType(recordMap.get("enterpriseType").toString());
                productsDetailRes.setStoreInfoStoreQrCode(recordMap.get("storeInfoStoreQrCode").toString());
                productsDetailRes.setGmtCreateTime(recordMap.get("gmtCreateTime").toString());
                productDetailResList.add(productsDetailRes);
                categorys.put(recordMap.get("fThreeCategoryCode").toString(), recordMap.get("fThreeCategoryName").toString());
                brands.put(recordMap.get("proSkuBrandId").toString(), recordMap.get("bBrandName").toString());
                if (attributes.containsKey(recordMap.get("attributeName").toString())) {
                    Map<String, String> mapt = attributes.get(recordMap.get("attributeName").toString());
                    mapt.put(recordMap.get("attributeValueId").toString(), recordMap.get("value_Name").toString());
                } else {
                    Map<String, String> mapt = new HashMap<>();
                    mapt.put(recordMap.get("attributeValueId").toString(), recordMap.get("value_Name").toString());
                    attributes.put(recordMap.get("attributeName").toString(), mapt);
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
    public ResponseBO<ProductDetailsRes> getProductsDetail(@Valid ProductDetailsReq productDetailsReq) {
        ResponseBO<ProductDetailsRes> res = new ResponseBO<ProductDetailsRes>();
        ProductDetailsRes productDetailsRes = new ProductDetailsRes();
        try {
                ProductsTO productsTO = productESServiceImpl.findById(productDetailsReq.getProductId());
                productDetailsRes.setSkuTitle(productsTO.getProSkuTitle());
                productDetailsRes.setSkuSubTitle(productsTO.getProSkuSubTitle());
                productDetailsRes.setPriceJson(productsTO.getSkuSellPriceJson());
                productDetailsRes.setPriceType(productsTO.getSkuSellPriceType());
                productDetailsRes.setSkuIntroduce(productsTO.getSkuIntroduce());
                productDetailsRes.setProSkuSkuPic(productsTO.getProSkuSkuPic());
                productDetailsRes.setRegionalId(productsTO.getRegionalId());
                productDetailsRes.setRegionalName(productsTO.getRegionalName());

                Map<String, Map<String, String>> attributes = new HashMap<>(); //属性
                Map<String,String> map = new HashMap<>();
                map.put(productsTO.getAttributeName(),productsTO.getValue_Name());
                attributes.put(productsTO.getAttributeId()+productsTO.getAttributeType(),map);
                productDetailsRes.setAttributeMap(attributes);
            } catch(IOException e){
                e.printStackTrace();
            }


            return res;
    }

    @Override
    public ResponseBO<CategoryRes> categoryRes(@Valid CategoryReq categoryReq) {


        ResponseBO<CategoryRes> res = new ResponseBO<CategoryRes>();
        CategoryRes categoryRes = new CategoryRes();
        CategoryVO categoryVO = new CategoryVO();
        Map map = new HashMap();

        try {
            ProductsTO productsTO = productESServiceImpl.findById(categoryRes.getSkuId());

            categoryRes.setSkuId(productsTO.getSkuId());

            categoryVO.setFOneCategoryId(productsTO.getFOneCategoryId());
            categoryVO.setFOneCategoryCode(productsTO.getFOneCategoryCode());
            categoryVO.setFOneCategoryName(productsTO.getFOneCategoryName());
            categoryVO.setFTwoCategoryId(productsTO.getFTwoCategoryId());
            categoryVO.setFTwoCategoryCode(productsTO.getFTwoCategoryCode());
            categoryVO.setFTwoCategoryName(productsTO.getFTwoCategoryName());
            categoryVO.setFThreeCategoryId(productsTO.getFThreeCategoryId());
            categoryVO.setFThreeCategoryCode(productsTO.getFThreeCategoryCode());
            categoryVO.setFThreeCategoryName(productsTO.getFThreeCategoryName());

            categoryRes.setCategoryVO(categoryVO);
            map.put(productsTO.getSkuId(),categoryVO);
            categoryRes.setMaplist(map);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return res;
    }
}
