package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.CoustemerReq;
import com.vd.canary.data.api.request.es.ShopPageReq;
import com.vd.canary.data.api.request.es.ShopSearchReq;
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
public class ShopServeiceFallbackFactory implements FallbackFactory<ShopServeiceFeign> {
    @Override
    public ShopServeiceFeign create(Throwable e) {
        return new ShopServeiceFeign() {

            /**
             * 商铺搜索
             *
             * @param shopSearchBO
             */
            @Override
            public ResponseBO<ShopPageReq> search(@Valid ShopSearchReq shopSearchBO) {
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
             * @param coustemerBO
             */
            @Override
            public ResponseBO<ShopPageReq> getID(@Valid CoustemerReq coustemerBO) {
                return null;
            }
        };
    }
}