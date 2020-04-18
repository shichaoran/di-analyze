package com.vd.canary.data.service.es.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.util.ResponseUtil;
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
import com.vd.canary.utils.CollectionUtil;
import com.vd.canary.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Slf4j
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductESServiceImpl productESServiceImpl;

    //@Override
    public ResponseBO<ProductsRes> getProductsByKey1(@Valid ProductsReq productsReq) throws Exception {
        ResponseBO<ProductsRes> res = new ResponseBO<ProductsRes>();
        ProductsRes productsRes = new ProductsRes();
        Map<String,String> categorys = new HashMap<String,String>();
        categorys.put("0","装配式建筑");
        categorys.put("1","金属矿");
        categorys.put("2","铜矿");
        productsRes.setCategorys(categorys);
        Map<String,String> brands = new HashMap<String,String>();
        brands.put("0","抚顺特钢");
        brands.put("1","不锈钢");
        brands.put("2","不锈钢铁");
        productsRes.setBrands(brands);
        Map<String,Map<String,String>> s= new HashMap<String,Map<String,String>>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("1","红色");
        map.put("2","黄色");
        s.put("颜色",map);
        productsRes.setAttributes(s);
        List<ProductsDetailRes> list = new ArrayList<ProductsDetailRes>();
        ProductsDetailRes productsDetailRes = new ProductsDetailRes();
        productsDetailRes.setSkuId("1");
        Map<String, Map<String, String>> s1 = new HashMap<>();
        Map<String,String> mapsub = new HashMap<String,String>();
        mapsub.put("1","黑色");
        mapsub.put("2","白色");
        s1.put("颜色",mapsub);
        productsDetailRes.setAttributeMap(s1);
        productsDetailRes.setSkuName("建筑钢材");
        productsDetailRes.setProSkuTitle("建筑钢");
        productsDetailRes.setProSkuSubTitle("建筑钢特殊钢");
        productsDetailRes.setProSkuSkuPicJson("[{\"fileSortNumber\":\"1\",\"fileUrl\":\"www.baidu.com\"}]");
        productsDetailRes.setSkuSellPriceJson("[\"100\"]");
        productsDetailRes.setSkuSellPriceType(0);
        productsDetailRes.setSkuGmtCreateTime(null);
        productsDetailRes.setSkuAuxiliaryUnit("顿");
        productsDetailRes.setFThreeCategoryName("建筑钢");
        productsDetailRes.setShopId("1250735825574989826");
        productsDetailRes.setStoreInfoName("安徽商和融资担保有限公司");
        productsDetailRes.setBusinessCategory("100");
        productsDetailRes.setMainProducts("建筑钢 钢材");
        productsDetailRes.setBusinessArea("安徽");
        productsDetailRes.setBoothBusinessBoothCode("[\"100#\"]");
        productsDetailRes.setCustomerProfilesLevel("1");
        productsDetailRes.setApproveState("1");
        productsDetailRes.setEnterpriseType("2");
        productsDetailRes.setStoreInfoStoreQrCode("=97dhkrnjdkwndk83dd9da72lj3 ");
        productsDetailRes.setGmtCreateTime(LocalDateTime.ofInstant(DateUtil.currentDate().toInstant(), ZoneId.systemDefault()));
        productsDetailRes.setBoothScheduledTime(LocalDateTime.ofInstant(DateUtil.currentDate().toInstant(), ZoneId.systemDefault()));
        list.add(productsDetailRes);
        productsRes.setProductDetailRes(list);

        productsRes.setTotal(100);
        res.setData(productsRes);
        res.setCode(200);
        res.setSuccess(true);
        res.setMessage("success");
        return res;
    }

    @Override
    public ResponseBO<ProductsRes> getProductsByKey(@Valid ProductsReq productsReq) throws Exception {
        ResponseBO<ProductsRes> res = new ResponseBO<ProductsRes>();
        ProductsRes productsRes = new ProductsRes();
        log.info("getProductsByKey,productsReq:" + JSONObject.toJSON(productsReq).toString());
        ESPageRes esPageRes = productESServiceImpl.boolQueryByKeyword(productsReq.getPageNum(), productsReq.getPageSize(), productsReq);
        if (esPageRes!=null) {
            List<Map<String, Object>> recordList = esPageRes.getRecordList();
            if (recordList != null && recordList.size() > 0) {
                Map<String, String> categorys = new HashMap<>();//fThreeCategoryId:fThreeCategoryName
                Map<String, String> brands = new HashMap<>(); //proSkuBrandId:bBrandName
                Map<String, Map<String, String>> attributes = new HashMap<>(); //属性
                List<ProductsDetailRes> productDetailResList = new ArrayList<>(); //商品详细列表
                for (Map<String, Object> recordMap : recordList) {
                    ProductsDetailRes productsDetailRes = new ProductsDetailRes();
                    productsDetailRes.setSkuId(recordMap.get("skuId").toString());
                    productsDetailRes.setProSkuTitle(recordMap.containsKey("proSkuTitle") ? recordMap.get("proSkuTitle").toString() : "");
                    productsDetailRes.setProSkuSubTitle(recordMap.containsKey("proSkuSubTitle") ? recordMap.get("proSkuSubTitle").toString() : "");
                    productsDetailRes.setProSkuSkuPicJson(recordMap.containsKey("proSkuSkuPic") ? recordMap.get("proSkuSkuPic").toString() : "");
                    productsDetailRes.setSkuSellPriceJson(recordMap.containsKey("skuSellPriceJson") ? recordMap.get("skuSellPriceJson").toString() : "");
                    productsDetailRes.setSkuSellPriceType(recordMap.containsKey("skuSellPriceType") ? Integer.parseInt(recordMap.get("skuSellPriceType").toString()) : 0);
                    if (recordMap.containsKey("skuGmtCreateTime")) {
                        productsDetailRes.setSkuGmtCreateTime(LocalDateTime.parse(recordMap.get("skuGmtCreateTime").toString()));
                    }
                    productsDetailRes.setShopId(recordMap.containsKey("storeId") ? recordMap.get("storeId").toString() : "");
                    productsDetailRes.setStoreInfoName(recordMap.containsKey("storeName") ? recordMap.get("storeName").toString() : "");
                    productsDetailRes.setBusinessCategory(recordMap.containsKey("businessCategory") ? recordMap.get("businessCategory").toString() : "");
                    productsDetailRes.setMainProducts(recordMap.containsKey("mainProducts") ? recordMap.get("mainProducts").toString() : "");
                    productsDetailRes.setBusinessArea(recordMap.containsKey("businessArea") ? recordMap.get("businessArea").toString() : "");
                    productsDetailRes.setBoothBusinessBoothCode(recordMap.containsKey("boothBusinessBoothCode") ? recordMap.get("boothBusinessBoothCode").toString() : "");
                    productsDetailRes.setCustomerProfilesLevel(recordMap.containsKey("customerProfilesLevel") ? recordMap.get("customerProfilesLevel").toString() : "");
                    productsDetailRes.setApproveState(recordMap.containsKey("approveState") ? recordMap.get("approveState").toString() : "");
                    productsDetailRes.setEnterpriseType(recordMap.containsKey("enterpriseType") ? recordMap.get("enterpriseType").toString() : "");
                    productsDetailRes.setStoreInfoStoreQrCode(recordMap.containsKey("storeInfoStoreQrCode") ? recordMap.get("storeInfoStoreQrCode").toString() : "");

                    if (recordMap.containsKey("gmtCreateTime")) {
                        productsDetailRes.setGmtCreateTime(LocalDateTime.parse(recordMap.get("gmtCreateTime").toString()));
                    }
                    productDetailResList.add(productsDetailRes);
                    categorys.put(recordMap.containsKey("fThreeCategoryCode") ? recordMap.get("fThreeCategoryCode").toString() : "", recordMap.containsKey("fThreeCategoryName") ? recordMap.get("fThreeCategoryName").toString() : "");
                    brands.put(recordMap.containsKey("proSkuBrandId") ? recordMap.get("proSkuBrandId").toString() : "", recordMap.containsKey("bBrandName") ? recordMap.get("bBrandName").toString() : "");
                    if (attributes.containsKey(recordMap.containsKey("attributeName") ? recordMap.get("attributeName").toString() : "")) {
                        Map<String, String> mapt = attributes.get(recordMap.containsKey("attributeName") ? recordMap.get("attributeName").toString() : "");
                        mapt.put(recordMap.containsKey("attributeValueId") ? recordMap.get("attributeValueId").toString() : "", recordMap.containsKey("value_Name") ? recordMap.get("value_Name").toString() : "");
                    } else {
                        Map<String, String> mapt = new HashMap<>();
                        mapt.put(recordMap.containsKey("attributeValueId") ? recordMap.get("attributeValueId").toString() : "", recordMap.containsKey("value_Name") ? recordMap.get("value_Name").toString() : "");
                        attributes.put(recordMap.containsKey("attributeName") ? recordMap.get("attributeName").toString() : "", mapt);
                    }
                }
                productsRes.setCategorys(categorys);
                productsRes.setBrands(brands);
                productsRes.setAttributes(attributes);
                productsRes.setProductDetailRes(productDetailResList);
                productsRes.setTotal(esPageRes.getRecordCount());
            }
        }
        res.setData(productsRes);
        res.setSuccess(true);
        res.setCode(200);
        res.setMessage("success.");
        return res;
    }

    //@Override
    public ResponseBO<ProductsRes> getProductByCategory1(@Valid ThreeCategoryReq threeCategoryReq) throws Exception {
        ProductsReq productsReq = new ProductsReq();
        return getProductsByKey(productsReq);
    }
    @Override
    public ResponseBO<ProductsRes> getProductByCategory(@Valid ThreeCategoryReq threeCategoryReq) throws Exception {
        ResponseBO<ProductsRes> res = new ResponseBO<ProductsRes>();
        ProductsRes productsRes = new ProductsRes();
        log.info("getProductByCategory,threeCategoryReq:"+JSONObject.toJSON(threeCategoryReq).toString());
        ESPageRes esPageRes = productESServiceImpl.boolQueryByDiffCategorys(threeCategoryReq.getPageNum(), threeCategoryReq.getPageSize(), new ThreeCategoryReq());
        if (esPageRes!=null) {
            List<Map<String, Object>> recordList = esPageRes.getRecordList();
            if (recordList != null && recordList.size() > 0) {
                Map<String, String> categorys = new HashMap<>();//fThreeCategoryId:fThreeCategoryName
                Map<String, String> brands = new HashMap<>(); //proSkuBrandId:bBrandName
                Map<String, Map<String, String>> attributes = new HashMap<>(); //属性
                List<ProductsDetailRes> productDetailResList = new ArrayList<>(); //商品详细列表
                for (Map<String, Object> recordMap : recordList) {
                    ProductsDetailRes productsDetailRes = new ProductsDetailRes();
                    productsDetailRes.setSkuId(recordMap.containsKey("skuId") ? recordMap.get("skuId").toString() : "");
                    productsDetailRes.setProSkuTitle(recordMap.containsKey("proSkuTitle") ? recordMap.get("proSkuTitle").toString() : "");
                    productsDetailRes.setProSkuSubTitle(recordMap.containsKey("proSkuSubTitle") ? recordMap.get("proSkuSubTitle").toString() : "");
                    productsDetailRes.setProSkuSkuPicJson(recordMap.containsKey("proSkuSkuPic") ? recordMap.get("proSkuSkuPic").toString() : "");
//                    productsDetailRes.setAttributeMap(recordMap.containsKey("attributeMap") ? recordMap.get("attributeMap").toString() : "");
                    productsDetailRes.setSkuSellPriceJson(recordMap.containsKey("skuSellPriceJson") ? recordMap.get("skuSellPriceJson").toString() : "");
                    productsDetailRes.setSkuSellPriceType(recordMap.containsKey("skuSellPriceType") ? Integer.parseInt(recordMap.get("skuSellPriceType").toString()) : 0);
                    if (recordMap.containsKey("skuGmtCreateTime")) {
                        productsDetailRes.setSkuGmtCreateTime(LocalDateTime.parse(recordMap.get("skuGmtCreateTime").toString()));
                    }
//                productsDetailRes.setSkuGmtCreateTime(DateUtil.getDateTime(recordMap.containsKey("skuGmtCreateTime")?recordMap.get("skuGmtCreateTime").toString():""));
                    productsDetailRes.setShopId(recordMap.containsKey("storeId") ? recordMap.get("storeId").toString() : "");
                    productsDetailRes.setStoreInfoName(recordMap.containsKey("storeName") ? recordMap.get("storeName").toString() : "");
                    productsDetailRes.setBusinessCategory(recordMap.containsKey("businessCategory") ? recordMap.get("businessCategory").toString() : "");
                    productsDetailRes.setMainProducts(recordMap.containsKey("mainProducts") ? recordMap.get("mainProducts").toString() : "");
                    productsDetailRes.setBusinessArea(recordMap.containsKey("businessArea") ? recordMap.get("businessArea").toString() : "");
                    productsDetailRes.setBoothBusinessBoothCode(recordMap.containsKey("boothBusinessBoothCode") ? recordMap.get("boothBusinessBoothCode").toString() : "");
                    productsDetailRes.setCustomerProfilesLevel(recordMap.containsKey("customerProfilesLevel") ? recordMap.get("customerProfilesLevel").toString() : "");
                    productsDetailRes.setApproveState(recordMap.containsKey("approveState") ? recordMap.get("approveState").toString() : "");
                    productsDetailRes.setEnterpriseType(recordMap.containsKey("enterpriseType") ? recordMap.get("enterpriseType").toString() : "");
                    productsDetailRes.setStoreInfoStoreQrCode(recordMap.containsKey("storeInfoStoreQrCode") ? recordMap.get("storeInfoStoreQrCode").toString() : "");

                    if (recordMap.containsKey("gmtCreateTime")) {
                        productsDetailRes.setGmtCreateTime(LocalDateTime.parse(recordMap.get("gmtCreateTime").toString()));
                    }
//                productsDetailRes.setGmtCreateTime(DateUtil.getDateTime(recordMap.containsKey("gmtCreateTime")?recordMap.get("gmtCreateTime").toString():""));
                    productDetailResList.add(productsDetailRes);
                    categorys.put(recordMap.containsKey("fThreeCategoryCode") ? recordMap.get("fThreeCategoryCode").toString() : "", recordMap.containsKey("fThreeCategoryName") ? recordMap.get("fThreeCategoryName").toString() : "");
                    brands.put(recordMap.containsKey("proSkuBrandId") ? recordMap.get("proSkuBrandId").toString() : "", recordMap.containsKey("bBrandName") ? recordMap.get("bBrandName").toString() : "");
                    if (attributes.containsKey(recordMap.containsKey("attributeName") ? recordMap.get("attributeName").toString() : "")) {
                        Map<String, String> mapt = attributes.get(recordMap.containsKey("attributeName") ? recordMap.get("attributeName").toString() : "");
                        mapt.put(recordMap.containsKey("attributeValueId") ? recordMap.get("attributeValueId").toString() : "", recordMap.containsKey("value_Name") ? recordMap.get("value_Name").toString() : "");
                    } else {
                        Map<String, String> mapt = new HashMap<>();
                        mapt.put(recordMap.containsKey("attributeValueId") ? recordMap.get("attributeValueId").toString() : "", recordMap.containsKey("value_Name") ? recordMap.get("value_Name").toString() : "");
                        attributes.put(recordMap.containsKey("attributeName") ? recordMap.get("attributeName").toString() : "", mapt);
                    }

                }
                productsRes.setCategorys(categorys);
                productsRes.setBrands(brands);
                productsRes.setAttributes(attributes);
                productsRes.setProductDetailRes(productDetailResList);
                productsRes.setTotal(esPageRes.getRecordCount());
            }
        }
        res.setData(productsRes);
        res.setSuccess(true);
        res.setCode(200);
        res.setMessage("success.");
        return res;
    }

    //@Override
    public ResponseBO<ProductDetailsRes> getProductsDetail1(@Valid ProductDetailsReq productDetailsReq) throws IOException {
        ResponseBO<ProductDetailsRes> res = new ResponseBO();
        ProductDetailsRes detail = new ProductDetailsRes();
        Map<String, Map<String, String>> s = new HashMap<>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("1","红色");
        map.put("2","黄色");
        s.put("颜色",map);
        detail.setAttributeMap(s);
        detail.setPriceJson("[\"100\"]");
        detail.setPriceType(1);
        detail.setProSkuSkuPicJson("[{\"fileSortNumber\":\"1\",\"fileUrl\":\"www.baidu.com\"}]");
        detail.setRegionalId("100");
        detail.setRegionalName("浙江");
        detail.setSkuId("1");
        detail.setSkuIntroduce("建筑钢 特殊钢 高韧性复合钢");
        detail.setSkuName("建筑钢");
        detail.setSkuSubTitle("建筑钢 特殊钢");
        detail.setSkuTitle("建筑钢材");
        res.setData(detail);
        res.setCode(200);
        res.setMessage("success");
        res.setSuccess(true);
        return res;
    }

    @Override
    public ResponseBO<ProductDetailsRes> getProductsDetail(@Valid ProductDetailsReq productDetailsReq) throws IOException {
        ResponseBO<ProductDetailsRes> res = new ResponseBO<ProductDetailsRes>();
        ProductDetailsRes productDetailsRes = new ProductDetailsRes();
        Map<String, Object> maps = productESServiceImpl.findById(productDetailsReq.getProductId());
        if (maps != null && maps.size() > 0) {
            if(maps.containsKey("skuId"))productDetailsRes.setSkuId(maps.get("skuId").toString());
            if(maps.containsKey("proSkuSkuName"))productDetailsRes.setSkuName(maps.get("proSkuSkuName").toString());
            if(maps.containsKey("proSkuTitle"))productDetailsRes.setSkuTitle(maps.get("proSkuTitle").toString());
            if(maps.containsKey("proSkuSubTitle"))productDetailsRes.setSkuSubTitle(maps.get("proSkuSubTitle").toString());
            if(maps.containsKey("attributeMap")){
                String temp = JSONObject.toJSONString(maps.get("attributeMap"));
                productDetailsRes.setAttributeMap(JSON.parseObject(temp,Map.class));
            }
            if(maps.containsKey("skuSellPriceJson"))productDetailsRes.setPriceJson(maps.get("skuSellPriceJson").toString());
            if(maps.containsKey("skuIntroduce"))productDetailsRes.setSkuIntroduce(maps.get("skuIntroduce").toString());
            if(maps.containsKey("proSkuSkuPicJson"))productDetailsRes.setProSkuSkuPicJson(maps.get("proSkuSkuPicJson").toString());
            if(maps.containsKey("regionalCode"))productDetailsRes.setRegionalId(maps.get("regionalCode").toString());
            if(maps.containsKey("regionalName"))productDetailsRes.setRegionalName(maps.get("regionalName").toString());
        }
        res.setData(productDetailsRes);
        res.setSuccess(true);
        res.setCode(200);
        res.setMessage("success.");
        return res;
    }

    public ResponseBO<CategoryRes> categoryRes1(@Valid CategoryReq categoryReq) {
        ResponseBO<CategoryRes> res = new ResponseBO<CategoryRes>();
        CategoryRes data = new CategoryRes();
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setFOneCategoryId("120");
        categoryVO.setFOneCategoryCode("120478");
        categoryVO.setFOneCategoryName("建筑钢");
        categoryVO.setFTwoCategoryId("248");
        categoryVO.setFTwoCategoryCode("248468");
        categoryVO.setFTwoCategoryName("钢管");
        categoryVO.setFThreeCategoryId("579");
        categoryVO.setFThreeCategoryCode("579303");
        categoryVO.setFThreeCategoryName("薄钢片");
        //data.setCategoryVO(categoryVO);
        Map<String, CategoryVO> map = new HashMap<>();
        map.put("1000",categoryVO);
        data.setMaplist(map);
        //data.setSkuId("1");
        res.setData(data);
        res.setMessage("success");
        res.setCode(200);
        res.setSuccess(true);
        return res;
    }
    @Override
    public ResponseBO<CategoryRes> categoryRes(@Valid CategoryReq categoryReq) {
        ResponseBO<CategoryRes> res = new ResponseBO<CategoryRes>();
        List<Map<String, Object>> result = productESServiceImpl.findByIds(categoryReq);
        if (CollectionUtil.isNotEmpty(result)) {
            CategoryRes categoryRes = new CategoryRes();
            String skuid = "";
            Map<String, CategoryVO> maps = new HashMap<>();
            for(Map<String, Object> map : result){
                CategoryVO categoryVO = new CategoryVO();
                if(map.containsKey("skuId")){
                    if(map.containsKey("fOneCategoryId")) categoryVO.setFOneCategoryId(map.get("fOneCategoryId").toString());
                    if(map.containsKey("fOneCategoryCode")) categoryVO.setFOneCategoryCode(map.get("fOneCategoryCode").toString());
                    if(map.containsKey("fOneCategoryName")) categoryVO.setFOneCategoryName(map.get("fOneCategoryName").toString());
                    if(map.containsKey("fTwoCategoryId")) categoryVO.setFTwoCategoryId(map.get("fTwoCategoryId").toString());
                    if(map.containsKey("fTwoCategoryCode")) categoryVO.setFTwoCategoryCode(map.get("fTwoCategoryCode").toString());
                    if(map.containsKey("fTwoCategoryName")) categoryVO.setFTwoCategoryName(map.get("fTwoCategoryName").toString());
                    if(map.containsKey("fThreeCategoryId")) categoryVO.setFThreeCategoryId(map.get("fThreeCategoryId").toString());
                    if(map.containsKey("fThreeCategoryCode")) categoryVO.setFThreeCategoryCode(map.get("fThreeCategoryCode").toString());
                    if(map.containsKey("fThreeCategoryName")) categoryVO.setFThreeCategoryName(map.get("fThreeCategoryName").toString());
                    maps.put(map.get("skuId").toString(),categoryVO);
                }
            }
            categoryRes.setMaplist(maps);
            res.setData(categoryRes);
        }
        res.setMessage("success");
        res.setCode(200);
        res.setSuccess(true);
        return res;
    }
}
