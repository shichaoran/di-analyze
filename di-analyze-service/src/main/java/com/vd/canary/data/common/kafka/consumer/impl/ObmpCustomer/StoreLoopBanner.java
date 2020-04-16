package com.vd.canary.data.common.kafka.consumer.impl.ObmpCustomer;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import com.vd.canary.data.common.kafka.consumer.impl.Function;
import com.vd.canary.obmp.customer.api.feign.booth.BoothBusinessFeignClient;
import com.vd.canary.obmp.customer.api.feign.store.StoreLoopBannerFeignClient;
import com.vd.canary.obmp.customer.api.request.customer.bo.store.StoreLoopBannerBO;
import com.vd.canary.obmp.customer.api.response.customer.vo.store.StoreLoopBannerVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shichaoran
 * @Date 2020/4/16 15:21
 * @Version
 */
public class StoreLoopBanner implements Function {
    private static final Logger logger = LoggerFactory.getLogger(BoothBusiness.class);
    @Autowired
    private StoreLoopBannerFeignClient storeLoopBannerFeignClient;
    @Override
    public void performES(String msg) {
        logger.info("BoothBusinessBoothCode.msg"+msg);
        ResponseBO create  = storeLoopBannerFeignClient.queryStoreLoopBanner("new StoreLoopBannerBO()");
        StoreLoopBannerBO storeLoopBannerBO = (StoreLoopBannerBO)create.getData();
        ShopVo shopVo = new ShopVo();
        shopVo.setImageName(storeLoopBannerBO.getImageName());
        shopVo.setImageUrl(storeLoopBannerBO.getImageUrl());
    }
}
