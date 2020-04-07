package com.vd.canary.data.api.feign.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ShopPageBO;
import org.springframework.cloud.openfeign.FeignClient;
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
public interface ShopServeiceFeign extends  FeignClient  {
    @PostMapping("/entityword/creat")
    ResponseBO<ShopPageBO> creatEntityWordByKey(@RequestBody @Valid ShopPageBO shopPageBO);

}
