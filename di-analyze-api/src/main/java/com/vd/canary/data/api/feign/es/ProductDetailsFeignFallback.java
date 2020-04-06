package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.exception.BusinessException;
import com.vd.canary.data.api.request.es.ProductDetailsReq;
import com.vd.canary.data.api.response.es.ProductDetailsRes;
import feign.hystrix.FallbackFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.vd.canary.core.constant.HttpResponseStatus.FEIGN_EXCEPTION;

public class ProductDetailsFeignFallback implements FallbackFactory<ProductDetailsFeign> {
    @Override
    public ProductDetailsFeign create(Throwable e) {
        return new ProductDetailsFeign() {
            @Override
            public ResponseBO<ProductDetailsRes> get(@NotNull String id) {
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }

            @Override
            public ResponseBO<ProductDetailsRes> getProductsDetail(@Valid ProductDetailsReq productsDetailsReq) {
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }
        };
    }
}
