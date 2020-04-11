package com.vd.canary.data.service.es.impl;

import com.alibaba.fastjson.JSON;
import com.vd.canary.data.api.feign.es.ProductsServiceFeign;
import com.vd.canary.data.api.request.es.ProductDetailsReq;
import com.vd.canary.data.api.response.es.CategoryRes;
import com.vd.canary.data.api.response.es.ProductDetailsRes;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.api.response.es.vo.CategoryVO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @Author WangRuilin
 * @Date 2020/4/11 17:34
 */
public class ProductsServiceServiceImpl implements Function {

    private static final Logger logger = LoggerFactory.getLogger(ProductsServiceServiceImpl.class);
    @Autowired
    private ProductsServiceFeign productsServiceFeign;

    @Override
    public void performES(String msg) {

        ProductsRes productsRes = new ProductsRes();
        productsRes.setFOneCategoryId("0");
        productsRes.setFOneCategoryCode("Q11");
        productsRes.setFOneCategoryName("装配式建筑");
        productsRes.setFTwoCategoryId("1");
        productsRes.setFTwoCategoryCode("Q11001");
        productsRes.setFTwoCategoryName("金属矿");
        productsRes.setFThreeCategoryId("2");
        productsRes.setFThreeCategoryCode("Q11001002");
        productsRes.setFThreeCategoryName("铜矿");

        productsRes.setProSkuBrandId("4");
        productsRes.setBBrandName("抚顺特钢");
        productsRes.setSkuId("2");
        productsRes.setProSkuTitle("不锈钢");
        productsRes.setProSkuSubTitle("不锈钢铁");
        productsRes.setProSkuSkuPic("http://123");
        Map<String, HashSet<String>> s= new HashMap<String, HashSet<String>>();
        HashSet set = new HashSet<String>();
        set.add("1");
        s.put("s",set);
        productsRes.setAttributeMap(s);
        productsRes.setSkuSellPriceJson("[{\"num\": \"100\", \"price\": \"100.00\", \"referencePrice\": \"100.00\"}]");
        productsRes.setSkuSellPriceType("1");
        productsRes.setSkuGmtCreateTime("2020-04-11");

        productsRes.setShopId("2");
        productsRes.setStoreInfoName("钢材厂");
        productsRes.setBusinessCategory("22");
        productsRes.setMainProducts("油漆");
        productsRes.setBusinessArea("河北");
        List list = new ArrayList();
        list.add("aa");
        productsRes.setBoothBusinessBoothCode(list);
        productsRes.setCustomerProfilesLevel("22");
        productsRes.setApproveState("003758e0408f7a6b17ad00b6e7a02b95");
        productsRes.setEnterpriseType("1");
        productsRes.setStoreInfoStoreQrCode("ewq");
        productsRes.setGmtCreateTime("2020-04-11");

        CategoryRes categoryRes = new CategoryRes();
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setFOneCategoryId("11");
        categoryVO.setFOneCategoryCode("Q11");
        categoryVO.setFOneCategoryName("装配式建筑");
        categoryVO.setFTwoCategoryId("1");
        categoryVO.setFTwoCategoryCode("Q11001");
        categoryVO.setFTwoCategoryName("金属矿");
        categoryVO.setFThreeCategoryId("2");
        categoryVO.setFThreeCategoryCode("Q11001002");
        categoryVO.setFThreeCategoryName("铜矿");
        categoryRes.setSkuId("1");
        categoryRes.setCategoryVO(categoryVO);
        Map<String, CategoryVO> maplist = new HashMap<String, CategoryVO>();
        maplist.put("1",categoryVO);
        categoryRes.setMaplist(maplist);


        ProductDetailsRes productDetailsRes = new ProductDetailsRes();

        productDetailsRes.setSkuTitle("不锈钢");
        productDetailsRes.setSkuSubTitle("不锈钢铁");

        Map<String, HashSet<String>> ss= new HashMap<String, HashSet<String>>();
        HashSet sets = new HashSet<String>();
        sets.add("1");
        ss.put("s",sets);
        productDetailsRes.setAttributeMap(ss);
        productDetailsRes.setPriceJson("[{\"num\": \"100\", \"price\": \"100.00\", \"referencePrice\": \"100.00\"}]");
        productDetailsRes.setPriceType("1");
        productDetailsRes.setSkuIntroduce("钢铁图片");
        productDetailsRes.setProSkuSkuPic("http://123");
        productDetailsRes.setRegionalId("1");
        productDetailsRes.setRegionalName("北京");


    }

}
