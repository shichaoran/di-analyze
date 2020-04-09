package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.core.exception.BusinessException;
import com.vd.canary.data.api.request.es.CategoryReq;
import com.vd.canary.data.api.request.es.ProductDetailsReq;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.response.es.CategoryRes;
import com.vd.canary.data.api.response.es.ProductDetailsRes;
import com.vd.canary.data.api.response.es.ProductsRes;
import feign.hystrix.FallbackFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.vd.canary.core.constant.HttpResponseStatus.FEIGN_EXCEPTION;

public class ProductsFeignFallback implements FallbackFactory<ProductsFeign> {


    @Override
    public ProductsFeign create(Throwable e) {
        return new ProductsFeign() {
            @Override
            public ResponsePageBO<ProductsRes> getProductsByKey(@Valid ProductsReq productsReq) {
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }

            @Override
            public ResponseBO<CategoryRes> categoryres(@Valid CategoryReq categoryReq) {
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }

            @Override
            public ResponseBO<ProductDetailsRes> get(@NotNull String id) {
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }

            @Override
            public ResponseBO<ProductDetailsRes> getProductsDetail(@Valid ProductDetailsReq productDetailsReq) {
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }
        };
    }
}
