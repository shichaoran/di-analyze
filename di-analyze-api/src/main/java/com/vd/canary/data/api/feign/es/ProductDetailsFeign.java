package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ProductDetailsReq;
import com.vd.canary.data.api.response.es.ProductDetailsRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@FeignClient(value = "canary-dataanalyze",
        contextId = "productDetailsFeign",
        decode404 = true,
        fallbackFactory = ProductDetailsFeignFallback.class)

public interface ProductDetailsFeign extends Feign {

    /**
     *
     * @param id
     * @return ResponseBO<ProductDetailsRes>
     */
    @GetMapping
    ResponseBO<ProductDetailsRes> get(@PathVariable("id") @NotNull String id);
    @PostMapping("/es/getProductsDetail")
    ResponseBO<ProductDetailsRes> getProductsDetail(@RequestBody @Valid ProductDetailsReq productDetailsReq);


}
