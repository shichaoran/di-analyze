package com.vd.canary.data.controller.es;


import com.sun.istack.NotNull;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.util.ResponseUtil;
import com.vd.canary.data.api.response.es.ESEntityWordRes;
import com.vd.canary.data.service.es.ESEntityWordService;
import com.vd.canary.service.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@Component
public class ESEntityWordController extends BaseController {

    private ESEntityWordService esEntityWordService;

    @GetMapping("/entityword/get/{id}")
    public ResponseBO<ESEntityWordRes> get(@PathVariable("id") @NotNull String id){
        ESEntityWordRes esEntityWordRes =new ESEntityWordRes();
        esEntityWordRes.setKey("111");
        esEntityWordRes.setValue("测试");
         return ResponseUtil.ok(esEntityWordRes);
    }

}
