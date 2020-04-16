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

import static org.apache.logging.log4j.ThreadContext.get;

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
            ShopProductRes shopProductRes = new ShopProductRes();
            ShopVo shopVo = new ShopVo();
            ShopBrand shopBrand = new ShopBrand();
            for (Map<String, Object> recordMap : recordList) {

                shopVo.setBusinessArea(recordMap.containsKey("businessArea")?recordMap.get("businessArea").toString():"");
                shopVo.setMainProducts(recordMap.containsKey("mainProducts")?recordMap.get("mainProducts").toString():"");
                shopVo.setBusinessCategory(recordMap.containsKey("businessCategory")?recordMap.get("businessCategory").toString():"");
                shopVo.setCustomerId(recordMap.containsKey("customerId")?recordMap.get("customerId").toString():"");
                shopVo.setStoreTemplateId(recordMap.containsKey("storeTemplateId")?recordMap.get("storeTemplateId").toString():"");
                shopVo.setMediaUrl(recordMap.containsKey("mediaUrl")?recordMap.get("mediaUrl").toString():"");
                shopVo.setBoothCode(Collections.singletonList(recordMap.containsKey("boothCode")?recordMap.get("boothCode").toString():""));
                shopVo.setId(recordMap.containsKey("id")?recordMap.get("id").toString():"");
                shopVo.setImageUrl(recordMap.containsKey("imageUrl")?recordMap.get("imageUrl").toString():"");
                shopVo.setName(recordMap.containsKey("name")?recordMap.get("name").toString():"");
                if (recordMap.containsKey("boothScheduledTime")) {
                    shopVo.setBoothScheduledTime(LocalDateTime.parse(recordMap.get("boothScheduledTime").toString()));
                }
                shopVo.setBusinessBrand(Collections.singletonList(recordMap.containsKey("businessBrand")?recordMap.get("businessBrand").toString():""));
                shopVo.setLevel(recordMap.containsKey("level")?recordMap.get("level").toString():"");
                shopVo.setImageOrder(recordMap.containsKey("imageOrder")?recordMap.get("imageOrder").toString():"");
                shopVo.setImageName(recordMap.containsKey("imageName")?recordMap.get("imageName").toString():"");
                shopProductRes.setPriceType(recordMap.containsKey("priceType")?recordMap.get("priceType").toString():"");
                shopProductRes.setSkuName(recordMap.containsKey("skuName")?recordMap.get("skuName").toString():"");
                shopProductRes.setSkuPrice(recordMap.containsKey("skuPrice")?recordMap.get("skuPrice").toString():"");
                shopProductRes.setSkuSubtitle(recordMap.containsKey("skuSubtitle")?recordMap.get("skuSubtitle").toString():"");
                shopProductRes.setSkuTitle(recordMap.containsKey("skuTitle")?recordMap.get("skuTitle").toString():"");
                shopProductRes.setUnit(recordMap.containsKey("unit")?recordMap.get("unit").toString():"");
                shopProductRes.setSkuId(recordMap.containsKey("skuId")?recordMap.get("skuId").toString():"");
                shopProductRes.setSkuPic(recordMap.containsKey("skuPic")?recordMap.get("skuPic").toString():"");
                shopBrand.setBrandId(recordMap.containsKey("brandId")?recordMap.get("brandId").toString():"");
                shopBrand.setBrandName(recordMap.containsKey("brandName")?recordMap.get("brandName").toString():"");
                brands.put(recordMap.containsKey("brandId")?recordMap.get("brandId").toString():"",recordMap.containsKey("brandName")?recordMap.get("brandName").toString():"");
                categories.put(recordMap.containsKey("CategoryId")?recordMap.get("CategoryId").toString():"",recordMap.containsKey("CategoryName")?recordMap.get("CategoryName").toString():"");
                List<ShopProductRes>shopProductRess = new ArrayList<>();
                shopProductRess.add(shopProductRes);

                shopVo.setShopProductRes(shopProductRess);
                shopVos.add(shopVo);

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


