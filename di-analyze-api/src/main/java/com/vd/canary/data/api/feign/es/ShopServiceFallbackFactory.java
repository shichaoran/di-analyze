package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.CustomerReq;
import com.vd.canary.data.api.request.es.ProductListReq;
import com.vd.canary.data.api.request.es.ShopPageReq;
import com.vd.canary.data.api.request.es.ShopSearchReq;
import com.vd.canary.data.api.response.es.ShopRes;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @Author shichaoran
 * @Date 2020/4/6 14:02
 * @Version
 */
@Slf4j
@Component
public class ShopServiceFallbackFactory implements FallbackFactory<ShopServiceFeign> {
    @Override
    public ShopServiceFeign create(Throwable e) {
        return new ShopServiceFeign() {


            /**
             * 商铺搜索
             *
             * @param shopSearchBO
             */
            @Override
            public ResponseBO<ShopRes> search(@Valid ShopSearchReq shopSearchBO) {
                return null;
            }

            /**
             * 商铺详情
             * 给shopid
             *
             * @param shopPageBO
             */
            @Override
            public ResponseBO<ShopPageReq> getByID(@Valid ShopPageReq shopPageBO) {
                return null;
            }

            /**
             * costemer id
             *
             * @param customerReq
             */
            @Override
            public ResponseBO<ShopPageReq> getID(@Valid CustomerReq customerReq) {
                return null;
            }

            /**
             * Detail
             * shangpin
             *
             * @param productList
             */
            @Override
            public ResponseBO<ShopRes> getList(@Valid ProductListReq productList) {
                return null;
            }
        };
    }
}