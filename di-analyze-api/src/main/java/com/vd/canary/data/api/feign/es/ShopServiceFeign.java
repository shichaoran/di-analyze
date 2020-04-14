package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.CustomerReq;
import com.vd.canary.data.api.request.es.ProductListReq;
import com.vd.canary.data.api.request.es.ShopPageReq;
import com.vd.canary.data.api.request.es.ShopSearchReq;
import com.vd.canary.data.api.response.es.ShopProductRes;
import com.vd.canary.data.api.response.es.ShopRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author shichaoran
 * @Date 2020/4/6 10:18
 * @Version
 */
@FeignClient(value = "canary-dataanalyze",
        contextId = "ShopServiceFeign",
        decode404 = true,
        fallbackFactory = ShopServiceFallbackFactory.class)
public interface ShopServiceFeign extends Feign {
    /**
     * 商铺搜索
     */
    @PostMapping("/shop/search")
    ResponseBO<ShopRes> search(@RequestBody @Valid ShopSearchReq shopSearchBO);

    /**
     * 商铺详情
     * 给shopid
     */
    @GetMapping("/shop/product")
    ResponseBO<ShopPageReq> getByID(@RequestBody @Valid ShopPageReq shopPageBO);

    /**
     * costemer id
     */

    @GetMapping("/shop/customer")
    ResponseBO<ShopPageReq> getID(@RequestBody @Valid CustomerReq customerReq);

    /**
     * Detail
     * shangpin
     */
    @GetMapping("/shop/productDetail ")
    ResponseBO<ShopProductRes> getList(@RequestBody @Valid ProductListReq productList);
}
