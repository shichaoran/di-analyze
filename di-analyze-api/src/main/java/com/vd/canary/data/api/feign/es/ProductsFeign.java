package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.response.es.ProductsRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "canary-dataanalyze",
        contextId = "productsFeign",
        decode404 = true,
        fallbackFactory = ProductsFeignFallback.class)
public interface ProductsFeign extends Feign {
    /**
     *
     * @param productsReq
     * @return ResponsePageBO<ProductsRes>
     */
    @PostMapping("/es/getProductsByKey")
    ResponsePageBO<ProductsRes> getProductsByKey(@RequestBody @Valid ProductsReq productsReq);


}
