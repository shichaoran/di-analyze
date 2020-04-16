package com.vd.canary.data.service.es.impl;

import com.vd.canary.data.api.feign.es.ShopServiceFeign;
import com.vd.canary.data.api.response.es.ShopProductRes;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.api.response.es.vo.ShopSearchVO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.es.index.ShopTO;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

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
        Map map = new HashMap();
        List<ShopVo> list = new ArrayList<ShopVo>();
        map.put(111,"1111111");
        list.add(shopVO);
        ShopRes shopRes = new ShopRes();
        shopRes.setTotal(1111);
        shopTO.setBoothCode("1");
        shopTO.setBusinessArea("1");
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
        shopRes.setCategories(map);
        shopRes.setBrands(map);
        shopRes.setShopVos(list);
    }
}
