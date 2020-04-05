package com.vd.canary.data.api.feign;


import com.vd.canary.core.api.Feign;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.data.api.request.ESEntityWordReq;
import com.vd.canary.data.api.response.ESEntityWordListRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

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
