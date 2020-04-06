package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ShopPageBO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.lang.annotation.Annotation;

/**
 * @Author shichaoran
 * @Date 2020/4/6 14:02
 * @Version
 */
@Slf4j
@Component
public class ShopServeiceFallbackFactory implements FallbackFactory<ShopServeiceFeign> {

    @Override
    public ShopServeiceFeign create(Throwable throwable) {
        return new ShopServeiceFeign() {
            @Override
            public ResponseBO<ShopPageBO> creatEntityWordByKey(@Valid ShopPageBO shopPageBO) {
                return null;
            }

            @Override
            public String value() {
                return null;
            }

            @Override
            public String serviceId() {
                return null;
            }

            @Override
            public String contextId() {
                return null;
            }

            @Override
            public String name() {
                return null;
            }

            @Override
            public String qualifier() {
                return null;
            }

            @Override
            public String url() {
                return null;
            }

            @Override
            public boolean decode404() {
                return false;
            }

            @Override
            public Class<?>[] configuration() {
                return new Class[0];
            }

            @Override
            public Class<?> fallback() {
                return null;
            }

            @Override
            public Class<?> fallbackFactory() {
                return null;
            }

            @Override
            public String path() {
                return null;
            }

            @Override
            public boolean primary() {
                return false;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        };
    }
}
