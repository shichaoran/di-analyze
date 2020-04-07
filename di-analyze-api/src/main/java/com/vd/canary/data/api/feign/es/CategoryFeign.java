package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.CategoryReq;
import com.vd.canary.data.api.response.es.CategoryRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "canary-dataanalyze",
        contextId = "categoryFeign",
        decode404 = true,
        fallbackFactory = CategoryFeignFalllback.class)
public interface CategoryFeign extends Feign {
    @PostMapping("/es/categoryres")
    ResponseBO<CategoryRes> categoryres(@RequestBody @Valid CategoryReq categoryReq);

}
