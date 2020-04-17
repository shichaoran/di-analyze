package com.vd.canary.data.service.es.impl;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.SearchShopReq;
import com.vd.canary.data.api.response.es.ShopProductRes;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.api.response.es.vo.ProductsDetailRes;
import com.vd.canary.data.api.response.es.vo.ShopBrand;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.helper.ESPageRes;
import com.vd.canary.data.common.es.index.ShopTO;
import com.vd.canary.data.common.es.service.impl.ShopESServiceImpl;
import com.vd.canary.data.service.es.ShopService;
import com.vd.canary.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        ResponseBO<ShopRes> res = new ResponseBO<ShopRes>();
        ShopRes shopRes = new ShopRes();

        List<ShopVo> list = new ArrayList<>();
        ShopVo shopVO = new ShopVo();
        shopVO.setName("商店");
        shopVO.setId("2");
        List<String> booth = new ArrayList<>();
        booth.add("1");
        booth.add("2");
        shopVO.setBoothCode(booth);//List<String>"[\"100#\"]"
        shopVO.setMediaUrl("http://123");
        shopVO.setBusinessCategory("2");
        List<String> businessBrand = new ArrayList<>();
        businessBrand.add("品牌1");
        businessBrand.add("品牌2");
        shopVO.setBusinessBrand(businessBrand);//List<String>
        shopVO.setBusinessArea("北京");
        shopVO.setImageOrder("2");
        shopVO.setImageName("图片");
        shopVO.setImageUrl("http://234");
        List<ShopProductRes> products = new ArrayList<>();
        ShopProductRes shopProductRes = new ShopProductRes();
        shopProductRes.setSkuId("1");
        shopProductRes.setSkuName("铁");
        shopProductRes.setSkuPic("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E5%9B%BE%E7%89%87%20%E5%A3%81%E7%BA%B8&step_word=&hs=2&pn=0&spn=0&di=330&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=111713540%2C615806613&os=3275214250%2C2157200754&simid=3304861670%2C4081601877&adpicid=0&lpn=0&ln=3178&fr=&fmq=1389861203899_R&fm=&ic=0&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=wallpaper&bdtype=0&oriquery=%E5%9B%BE%E7%89%87&objurl=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1212%2F10%2Fc1%2F16491670_1355126816487.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3F2_z%26e3Brv5gstgj_z%26e3Bv54_z%26e3BvgAzdH3FkkfAzdH3Fp5rtv-cambb8bc_z%26e3Bip4s&gsm=1&rpstart=0&rpnum=0&islist=&querylist=&force=undefined\n");
        shopProductRes.setSkuPrice("2234");
        shopProductRes.setSkuTitle("标题");
        shopProductRes.setSkuSubtitle("副标题");
        shopProductRes.setUnit("米");
        shopProductRes.setPriceType("0");
        products.add(shopProductRes);
        shopVO.setShopProductRes(products);//List<ShopProductRes>
        HashSet<String> classifys = new HashSet<>();
        classifys.add("2");
        shopVO.setClassify(classifys);//HashSet<String>
        shopVO.setCustomerId("2");
        shopVO.setStoreTemplateId("1");
        shopVO.setMainProducts("钢铁");
        shopVO.setBoothScheduledTime(LocalDateTime.ofInstant(DateUtil.currentDate().toInstant(), ZoneId.systemDefault()));
        shopVO.setLevel("1");
        list.add(shopVO);
        shopRes.setShopVos(list);

        Map<String, String> brands = new HashMap<>();
        brands.put("1","品牌1");
        brands.put("2","品牌2");
        shopRes.setBrands(brands);

        Map<String, String> categories = new HashMap<>();
        categories.put("1","分类1");
        categories.put("2","分类2");
        shopRes.setCategories(categories);
        shopRes.setTotal(100);

        res.setData(shopRes);
        res.setCode(200);
        res.setSuccess(true);
        res.setMessage("success");
        return res;
    }


    //    @Override
    public ResponseBO<ShopRes> search1(@Valid SearchShopReq shopSearchBO) {
        ResponseBO<ShopRes> res = new ResponseBO<ShopRes>();
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

                shopVo.setBusinessArea(recordMap.containsKey("businessArea") ? recordMap.get("businessArea").toString() : "");
                shopVo.setMainProducts(recordMap.containsKey("mainProducts") ? recordMap.get("mainProducts").toString() : "");
                shopVo.setBusinessCategory(recordMap.containsKey("businessCategory") ? recordMap.get("businessCategory").toString() : "");
                shopVo.setCustomerId(recordMap.containsKey("customerId") ? recordMap.get("customerId").toString() : "");
                shopVo.setStoreTemplateId(recordMap.containsKey("storeTemplateId") ? recordMap.get("storeTemplateId").toString() : "");
                shopVo.setMediaUrl(recordMap.containsKey("mediaUrl") ? recordMap.get("mediaUrl").toString() : "");
                shopVo.setBoothCode(Collections.singletonList(recordMap.containsKey("boothCode") ? recordMap.get("boothCode").toString() : ""));
                shopVo.setId(recordMap.containsKey("id") ? recordMap.get("id").toString() : "");
                shopVo.setImageUrl(recordMap.containsKey("imageUrl") ? recordMap.get("imageUrl").toString() : "");
                shopVo.setName(recordMap.containsKey("name") ? recordMap.get("name").toString() : "");
                if (recordMap.containsKey("boothScheduledTime")) {
                    shopVo.setBoothScheduledTime(LocalDateTime.parse(recordMap.get("boothScheduledTime").toString()));
                }
                shopVo.setBusinessBrand(Collections.singletonList(recordMap.containsKey("businessBrand") ? recordMap.get("businessBrand").toString() : ""));
                shopVo.setLevel(recordMap.containsKey("level") ? recordMap.get("level").toString() : "");
                shopVo.setImageOrder(recordMap.containsKey("imageOrder") ? recordMap.get("imageOrder").toString() : "");
                shopVo.setImageName(recordMap.containsKey("imageName") ? recordMap.get("imageName").toString() : "");
                shopProductRes.setPriceType(recordMap.containsKey("priceType") ? recordMap.get("priceType").toString() : "");
                shopProductRes.setSkuName(recordMap.containsKey("skuName") ? recordMap.get("skuName").toString() : "");
                shopProductRes.setSkuPrice(recordMap.containsKey("skuPrice") ? recordMap.get("skuPrice").toString() : "");
                shopProductRes.setSkuSubtitle(recordMap.containsKey("skuSubtitle") ? recordMap.get("skuSubtitle").toString() : "");
                shopProductRes.setSkuTitle(recordMap.containsKey("skuTitle") ? recordMap.get("skuTitle").toString() : "");
                shopProductRes.setUnit(recordMap.containsKey("unit") ? recordMap.get("unit").toString() : "");
                shopProductRes.setSkuId(recordMap.containsKey("skuId") ? recordMap.get("skuId").toString() : "");
                shopProductRes.setSkuPic(recordMap.containsKey("skuPic") ? recordMap.get("skuPic").toString() : "");
                shopBrand.setBrandId(recordMap.containsKey("brandId") ? recordMap.get("brandId").toString() : "");
                shopBrand.setBrandName(recordMap.containsKey("brandName") ? recordMap.get("brandName").toString() : "");
                brands.put(recordMap.containsKey("brandId") ? recordMap.get("brandId").toString() : "", recordMap.containsKey("brandName") ? recordMap.get("brandName").toString() : "");
                categories.put(recordMap.containsKey("CategoryId") ? recordMap.get("CategoryId").toString() : "", recordMap.containsKey("CategoryName") ? recordMap.get("CategoryName").toString() : "");
                List<ShopProductRes> shopProductRess = new ArrayList<>();
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



