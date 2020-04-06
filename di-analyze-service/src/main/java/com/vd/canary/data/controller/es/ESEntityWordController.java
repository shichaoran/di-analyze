package com.vd.canary.data.controller.es;


import com.sun.istack.NotNull;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.response.es.ESEntityWordRes;
import com.vd.canary.data.service.es.ESEntityWordService;
import com.vd.canary.service.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
public class ESEntityWordController extends BaseController {
    @Autowired
    private ESEntityWordService esEntityWordService;

//    @GetMapping("/member/get/{id}")
//    public ResponseBO<AccountVO> get(@PathVariable @NotNull String id) {
//        return accountService.get(id);
//    }

    @GetMapping("/entityword/get/{id}")
    public ResponseBO<ESEntityWordRes> get(@PathVariable("id") @NotNull String id){
         return null;
    }

}
