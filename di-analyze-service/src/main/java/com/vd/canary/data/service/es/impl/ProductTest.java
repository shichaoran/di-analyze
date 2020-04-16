package com.vd.canary.data.service.es.impl;

import com.vd.canary.data.api.feign.es.ProductsServiceFeign;
import com.vd.canary.data.api.feign.es.ShopServiceFeign;
import com.vd.canary.data.api.response.es.CategoryRes;
import com.vd.canary.data.api.response.es.ProductDetailsRes;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.api.response.es.vo.CategoryVO;
import com.vd.canary.data.api.response.es.vo.ProductsDetailRes;
import com.vd.canary.data.common.es.model.ProductsTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductTest implements Function {

        private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProductTest.class);
        @Autowired
        private ProductsServiceFeign productsServiceFeign;

        @Override
        public void performES(String msg) {

            ProductsRes productsRes = new ProductsRes();
            ProductsTO productsTO = new ProductsTO();
            Map<String,String> categorys = new HashMap<String,String>();
            categorys.put("0","装配式建筑");
            categorys.put("1","金属矿");
            categorys.put("2","铜矿");
            productsRes.setCategorys(categorys);
            Map<String,String> brands = new HashMap<String,String>();
            categorys.put("0","抚顺特钢");
            categorys.put("1","不锈钢");
            categorys.put("2","不锈钢铁");
            productsRes.setBrands(brands);
            Map<String,Map<String,String>> s= new HashMap<String,Map<String,String>>();
            Map<String,String> map = new HashMap<String,String>();
            map.put("1","颜色");
            s.put("11",map);
            productsRes.setAttributes(s);
            List<ProductsDetailRes> list = new ArrayList<ProductsDetailRes>();
            ProductsDetailRes productsDetailRes = new ProductsDetailRes();
            productsDetailRes.setSkuId("1");
            productsDetailRes.setSkuName("铁2");
            productsDetailRes.setProSkuTitle("");
            productsDetailRes.setProSkuSubTitle("11");
            productsDetailRes.setProSkuSkuPicJson("dasf");
            productsDetailRes.setAttributeMap("sdfa");
            productsDetailRes.setSkuSellPriceJson("dsaf");
            productsDetailRes.setSkuSellPriceType(0);
            productsDetailRes.setSkuGmtCreateTime(null);
            productsDetailRes.setSkuAuxiliaryUnit("afea");
            productsDetailRes.setFThreeCategoryName("aefae");
            productsDetailRes.setShopId("aefa");
            productsDetailRes.setStoreInfoName("aae");
            productsDetailRes.setBusinessCategory("adfa");
            productsDetailRes.setMainProducts("aega");
            productsDetailRes.setBusinessArea("egrg");
            productsDetailRes.setBoothBusinessBoothCode("qwre");
            productsDetailRes.setCustomerProfilesLevel("afdsga");
            productsDetailRes.setApproveState("atae");
            productsDetailRes.setEnterpriseType("adag");
            productsDetailRes.setStoreInfoStoreQrCode("adgER");
            productsDetailRes.setGmtCreateTime(null);
            productsDetailRes.setBoothScheduledTime(null);
            list.add(productsDetailRes);

            productsRes.setTotal(100);


            CategoryRes categoryRes = new CategoryRes();

            CategoryVO categoryVO = new CategoryVO();

            categoryRes.setSkuId("2");
            categoryVO.setFOneCategoryName("rtwr");
            categoryVO.setFOneCategoryId("2");
            categoryVO.setFOneCategoryCode("aega");
            categoryVO.setFTwoCategoryName("argat");
            categoryVO.setFTwoCategoryCode("arga");
            categoryVO.setFTwoCategoryId("1");
            categoryVO.setFThreeCategoryName("ahrsrt");
            categoryVO.setFThreeCategoryCode("shgare");
            categoryVO.setFThreeCategoryId("2");

            categoryRes.setCategoryVO(categoryVO);
            categoryRes.setMaplist("1",categoryVO);


            ProductDetailsRes productDetailsRes = new ProductDetailsRes();
            productDetailsRes.setSkuTitle("");
            productDetailsRes.setSkuSubTitle("");
            Map<String,Map<String,String>> ss= new HashMap<String,Map<String,String>>();
            Map<String,String> mapp = new HashMap<String,String>();
            map.put("1","颜色");
            s.put("11",map);
            productDetailsRes.setAttributeMap(ss);
            productDetailsRes.setPriceJson("frseh");
            productDetailsRes.setPriceType(1);
            productDetailsRes.setSkuIntroduce("aewgaer");
            productDetailsRes.setProSkuSkuPicJson("asffa4t");
            productDetailsRes.setRegionalId("wea");
            productDetailsRes.setRegionalName("ahrsy");

        }

}
