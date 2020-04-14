package com.vd.canary.data.service.es.impl;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.response.es.ESPageRes;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.api.response.es.vo.ProductsDetailRes;
import com.vd.canary.data.common.es.service.impl.ProductESServiceImpl;
import com.vd.canary.data.service.es.ProductsReService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:57
 * @Version
 */
public class ProductsReServiceImp implements ProductsReService {
    @Autowired
    private ProductESServiceImpl productESServiceImpl;
    @Override
    public ResponseBO<ProductsRes> getProductByCategory(ProductsReq threeCategoryReq) {
        ResponseBO<ProductsRes> res = new ResponseBO<ProductsRes>();
        ProductsRes productsRes = new ProductsRes();
        ESPageRes esPageRes = productESServiceImpl.boolQueryByKeyword(threeCategoryReq.getPageNum(),threeCategoryReq.getPageSize(),threeCategoryReq);
        List<Map<String, Object>> recordList = esPageRes.getRecordList();
        if(recordList != null && recordList.size() > 0 ){
            Map<String,String> categorys = new HashMap<>();//fThreeCategoryId:fThreeCategoryName
            Map<String,String> brands = new HashMap<>(); //proSkuBrandId:bBrandName
            Map<String,Map<String,String>> attributes = new HashMap<>(); //属性
            List<ProductsDetailRes> productDetailResList = new ArrayList<>(); //商品详细列表
            for(Map<String, Object> recordMap: recordList) {
                ProductsDetailRes productsDetailRes = new ProductsDetailRes();
                productsDetailRes.setSkuId(recordMap.get("skuId").toString());
                productsDetailRes.setProSkuTitle(recordMap.get("proSkuTitle").toString());
                productsDetailRes.setProSkuSubTitle(recordMap.get("proSkuSubTitle").toString());
                productsDetailRes.setProSkuSkuPic(recordMap.get("proSkuSkuPic").toString());
                productsDetailRes.setAttributeMap(recordMap.get("attributeMap").toString());
                productsDetailRes.setSkuSellPriceJson(recordMap.get("skuSellPriceJson").toString());
                productsDetailRes.setSkuSellPriceType(recordMap.get("skuSellPriceType").toString());
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
                brands.put(recordMap.get("proSkuBrandId").toString(),recordMap.get("bBrandName").toString());
                if(attributes.containsKey(recordMap.get("attributeName").toString())){
                    Map<String,String> mapt = attributes.get(recordMap.get("attributeName").toString());
                    mapt.put(recordMap.get("attributeValueId").toString(),recordMap.get("value_Name").toString());
                }else{
                    Map<String,String> mapt = new HashMap<>();
                    mapt.put(recordMap.get("attributeValueId").toString(),recordMap.get("value_Name").toString());
                    attributes.put(recordMap.get("attributeName").toString(),mapt);
            }
        }
            productsRes.setCategorys(categorys);
            productsRes.setBrands(brands);
            productsRes.setAttributes(attributes);
            productsRes.setProductDetailRes(productDetailResList);

    }
        res.setData(productsRes);
        return res;
}
}