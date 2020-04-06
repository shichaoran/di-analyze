package com.vd.canary.data.api.feign.es;


import com.sun.istack.NotNull;
import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ESEntityWordReq;
import com.vd.canary.data.api.response.es.ESEntityWordRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "canary-dataanalyze",
        contextId = "esEntityWordFeignClient",
        decode404 = true,
        fallbackFactory = ESEntityWordFallbackFactory.class)
public interface ESEntityWordServiceFeign extends Feign {

    @GetMapping("/entityword/get/{id}")
    ResponseBO<ESEntityWordRes> get(@PathVariable("id") @NotNull String id);

    @GetMapping("/entityword/getByKey/{key}")
    ResponseBO<ESEntityWordRes> getByKey(@PathVariable("id") @NotNull String key);

    @PostMapping("/entityword/creat")
    ResponseBO<ESEntityWordRes> creatEntityWordByKey(@RequestBody @Valid ESEntityWordReq esEntityWordReq);


}
