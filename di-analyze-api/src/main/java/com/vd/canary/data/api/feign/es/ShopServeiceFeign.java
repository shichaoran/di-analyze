package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.CoustemerBO;
import com.vd.canary.data.api.request.es.ShopPageBO;
import com.vd.canary.data.api.request.es.ShopSearchBO;
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
        contextId = "ShopServeiceFeign",
        decode404 = true,
        fallbackFactory = ShopServeiceFallbackFactory.class)
public interface ShopServeiceFeign extends Feign {
    /**
     *商铺搜索
     */
    @PostMapping("/shop/search")
    ResponseBO<ShopPageBO> search(@RequestBody @Valid ShopSearchBO shopSearchBO);
    /**
     * 商铺详情
     * 给shopid
     */
    @GetMapping("/shop/product")
    ResponseBO<ShopPageBO> getByID(@RequestBody @Valid ShopPageBO shopPageBO);
    /**
     * costemer id
     */

    @GetMapping("/shop/coustemer")
    ResponseBO<ShopPageBO> getID(@RequestBody @Valid CoustemerBO coustemerBO);

}
