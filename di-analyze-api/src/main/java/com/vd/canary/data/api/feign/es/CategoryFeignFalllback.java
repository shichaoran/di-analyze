package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.exception.BusinessException;
import com.vd.canary.data.api.request.es.CategoryReq;
import com.vd.canary.data.api.request.es.ProductDetailsReq;
import com.vd.canary.data.api.response.es.CategoryRes;
import com.vd.canary.data.api.response.es.ProductDetailsRes;
import feign.hystrix.FallbackFactory;

import javax.validation.Valid;

import static com.vd.canary.core.constant.HttpResponseStatus.FEIGN_EXCEPTION;

public class CategoryFeignFalllback implements FallbackFactory<CategoryFeign> {

    @Override
    public CategoryFeign create(Throwable e) {
        return new CategoryFeign() {
            @Override
            public ResponseBO<CategoryRes> categoryres(@Valid CategoryReq categoryReq) {
                throw new BusinessException(FEIGN_EXCEPTION).append(e.getMessage());
            }
        };
    }
}
