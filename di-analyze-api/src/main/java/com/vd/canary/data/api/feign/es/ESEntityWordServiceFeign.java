package com.vd.canary.data.api.feign.es;


import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ESEntityWordReq;
import com.vd.canary.data.api.response.es.ESEntityWordListRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "canary-dataanalyze")
public interface ESEntityWordServiceFeign extends Feign {

    /**
     *
     * @param esEntityWordReq
     * @return ResponseBO<ESEntityWordListRes>
     */
    @PostMapping("/es/getEntityWord")
    ResponseBO<ESEntityWordListRes> getEntityWordByKey(@RequestBody @Valid ESEntityWordReq esEntityWordReq);


}
