package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.exception.BusinessException;
import com.vd.canary.data.api.request.es.ShopPageBO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.lang.annotation.Annotation;

import static com.vd.canary.core.constant.HttpResponseStatus.FEIGN_EXCEPTION;

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

            @Override
            public ResponseBO<ShopPageBO> search(@Valid ShopPageBO shopPageBO) {
                return null;
            }
            /**
             * 商铺详情
             *
             * @param shopPageBO
             */
            @Override
            public ResponseBO<ShopPageBO> getByKey(@Valid ShopPageBO shopPageBO) {
                return null;
            }
        };
    }
}