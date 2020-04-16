package com.vd.canary.data.service.es.impl;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.SearchShopReq;
import com.vd.canary.data.api.response.es.ShopProductRes;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.api.response.es.vo.ProductsDetailRes;
import com.vd.canary.data.api.response.es.vo.ShopBrand;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.helper.ESPageRes;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.service.es.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author shichaoran
 * @Date 2020/4/14 19:53
 * @Version
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    public ShopESServiceImpl shopESService;


    @Override
    public ResponseBO<ShopRes> search(@Valid SearchShopReq shopSearchBO) {
        ResponseBO<ShopRes> res = new  ResponseBO<ShopRes>();
        ShopRes shopRes = new ShopRes();
        ESPageRes esPageRes = shopESService.boolQueryByKeyword(shopSearchBO.getPageNum(), shopSearchBO.getPageSize(), shopSearchBO);
        List<Map<String, Object>> recordList = esPageRes.getRecordList();
        if (recordList != null && recordList.size() > 0) {
            Map<String, String> categories = new HashMap<>();
            Map<String, String> brands = new HashMap<>();
            Map<String, Map<String, String>> attributes = new HashMap<>(); //属性
            List<ShopVo> shopVos = new ArrayList<>();
            List<ShopProductRes>shopProductRess = new ArrayList<>();
            ShopProductRes shopProductRes = new ShopProductRes();
            ShopVo shopVo = new ShopVo();
            ShopBrand shopBrand = new ShopBrand();
            for (Map<String, Object> recordMap : recordList) {


                shopVo.setBusinessArea(recordMap.get("businessArea").toString());
                shopVo.setMainProducts(recordMap.get("mainProducts").toString());
                shopVo.setBusinessCategory(recordMap.get("businessCategory").toString());
                shopVo.setCustomerId(recordMap.get("customerId").toString());
                shopVo.setStoreTemplateId(recordMap.get("storeTemplateId").toString());
                shopVo.setMediaUrl(recordMap.get("mediaUrl").toString());
                shopVo.setBoothCode(Collections.singletonList(recordMap.get("boothCode").toString()));
                shopVo.setId(recordMap.get("id").toString());
                shopVo.setImageUrl(recordMap.get("imageUrl").toString());
                shopVo.setName(recordMap.get("name").toString());
                shopVo.setBoothScheduledTime(LocalDateTime.parse(recordMap.get("boothScheduledTime").toString()));
                shopVo.setBusinessBrand(Collections.singletonList(recordMap.get("businessBrand").toString()));
                shopVo.setLevel(recordMap.get("level").toString());
                shopVo.setImageOrder(recordMap.get("imageOrder").toString());
                shopVo.setImageName(recordMap.get("imageName").toString());
                shopProductRes.setPriceType(recordMap.get("priceType").toString());
                shopProductRes.setSkuName(recordMap.get("skuName").toString());
                shopProductRes.setSkuPrice(recordMap.get("skuPrice").toString());
                shopProductRes.setSkuSubtitle(recordMap.get("skuSubtitle").toString());
                shopProductRes.setSkuTitle(recordMap.get("skuTitle").toString());
                shopProductRes.setUnit(recordMap.get("unit").toString());
                shopProductRes.setSkuId(recordMap.get("skuId").toString());
                shopProductRes.setSkuPic(recordMap.get("skuPic").toString());
                shopBrand.setBrandId(recordMap.get("brandId").toString());
                shopBrand.setBrandName(recordMap.get("brandName").toString());
                brands.put(recordMap.get("brandId").toString(),recordMap.get("brandName").toString());
                categories.put(recordMap.get("CategoryId").toString(),recordMap.get("CategoryName").toString());
                shopProductRess.add(shopProductRes);
            }
            shopRes.setTotal(esPageRes.getRecordCount());
            shopRes.setBrands(brands);
            shopRes.setShopVos(shopVos);
            shopRes.setCategories(categories);
        }
        res.setData(shopRes);
        return res;
    }

}


