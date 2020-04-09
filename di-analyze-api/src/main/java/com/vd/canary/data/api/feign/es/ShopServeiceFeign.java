package com.vd.canary.data.api.feign.es;

import com.sun.istack.NotNull;
import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ShopPageBO;
import com.vd.canary.data.api.response.es.ESEntityWordRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author shichaoran
 * @Date 2020/4/6 10:18
 * @Version
 */
@FeignClient(value = "canary-dataanalyze",
        contextId = "ShopServeiceFeign",
        decode404 = true,
        fallbackFactory = ShopServeiceFallbackFactory.class)
public interface ShopServeiceFeign extends Feign {
    @PostMapping("/shop/search")
    ResponseBO<ShopPageBO> createshop(@RequestBody @Valid ShopPageBO shopPageBO);

    @GetMapping("/shop/product")
    ResponseBO<ShopPageBO> getByKey(@RequestBody @Valid ShopPageBO shopPageBO);

}
