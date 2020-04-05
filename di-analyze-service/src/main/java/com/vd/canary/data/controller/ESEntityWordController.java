package com.vd.canary.data.controller;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.core.util.ResponseUtil;
import com.vd.canary.data.api.request.ESEntityWordReq;
import com.vd.canary.data.service.ESEntityWordService;
import com.vd.canary.service.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/entityword")
@Slf4j
public class ESEntityWordController extends BaseController {
    @Autowired
    private ESEntityWordService esEntityWordService;

    @PostMapping("/assignRoleMenu")
    public ResponseBO assignRoleMenu(@Valid @RequestBody ESEntityWordReq esEntityWordReq){
        //return esEntityWordService.existEntityWord("");
        return null;
    }

}
