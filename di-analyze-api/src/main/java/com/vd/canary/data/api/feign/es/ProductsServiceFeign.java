package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.data.api.request.es.CategoryReq;
import com.vd.canary.data.api.request.es.ProductDetailsReq;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.response.es.CategoryRes;
import com.vd.canary.data.api.response.es.ProductDetailsRes;
import com.vd.canary.data.api.response.es.ProductsRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@FeignClient(value = "canary-dataanalyze",
        contextId = "productsFeign",
        decode404 = true,
        fallbackFactory = ProductsServiceFeignFallback.class)
public interface ProductsServiceFeign extends Feign {
    /**
     *
     * @param productsReq
     * @return ResponsePageBO<ProductsRes>
     */
    /**
     * 通过首页搜索框输入商品名称
     */
    @PostMapping("/products/getProductsByKey")
    ResponsePageBO<ProductsRes> getProductsByKey(@RequestBody @Valid ProductsReq productsReq);

    /**
     * 一组skuid  返回对应的类目
     */
    @PostMapping("/products/category")
    ResponseBO<CategoryRes> categoryres(@RequestBody @Valid CategoryReq categoryReq);

    /**
     * 根据商品id返回对应的商品
     */
    @GetMapping("/products/getProductById")
    ResponseBO<ProductDetailsRes> get(@PathVariable("id") @NotNull String id);

    /**
     * 商品详情页
     */
    @PostMapping("/products/getProductDetail")
    ResponseBO<ProductDetailsRes> getProductsDetail(@RequestBody @Valid ProductDetailsReq productDetailsReq);

}
