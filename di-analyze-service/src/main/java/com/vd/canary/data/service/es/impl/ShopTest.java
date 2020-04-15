package com.vd.canary.data.service.es.impl;

import com.vd.canary.data.api.feign.es.ShopServiceFeign;
import com.vd.canary.data.api.response.es.ShopProductRes;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.index.ShopTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author shichaoran
 * @Date 2020/4/11 19:17
 * @Version
 */
public class ShopTest implements Function {
    private static final Logger logger = LoggerFactory.getLogger(ShopTest.class);
    @Autowired
    private ShopServiceFeign shopServiceFeign;
    @Override
    public void performES(String msg) {
        ShopTO shopTO = new ShopTO();
        ShopVo shopVO = new ShopVo();
        shopTO.setBoothCode("1");
        shopTO.setBusinessArea("1");
        List list = new ArrayList();
        list.add("1111");
        shopVO.setBusinessBrand(list);
        shopVO.setBusinessCategory("111");
        shopVO.setCustomerId("1112323");
        shopVO.setId("fdasfas1111");
        shopVO.setImageUrl("11111sdafsdhufiashjk");
        shopVO.setMediaUrl("www.wa.com");
        shopVO.setName("fffffff");
        shopVO.setStoreTemplateId("sddsfasdf");
        shopVO.setBoothCode(Collections.singletonList("1123343547"));
//        shopVO.setBoothScheduledTime("124324234");
        ShopProductRes shopProductRes = new ShopProductRes();
        shopProductRes.setUnit("sdfafgasdg");
        shopProductRes.setSkuSubtitle("dfgadfafaf");
        shopProductRes.setSkuPrice("fdasfasdfa");
//        shopProductRes.setSkupic("dffffff");
        shopProductRes.setSkuName("3ddddddd");
//        shopProductRes.setSkuID("33333333333");
        shopProductRes.setPriceType("1111111111111111111");
        shopProductRes.setSkuTitle("1111111");
    }
}
