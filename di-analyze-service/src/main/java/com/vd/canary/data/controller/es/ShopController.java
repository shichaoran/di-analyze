package com.vd.canary.data.controller.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.SearchShopReq;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.service.es.ShopService;
import com.vd.canary.service.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author shichaoran
 * @Date 2020/4/14 15:58
 * @Version
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopController extends BaseController {
    @Autowired
    private final ShopService shopService;


    @PostMapping("/shop/search")
    public ResponseBO<ShopRes> search(@RequestBody @Valid SearchShopReq shopSearchBO) {
        ResponseBO<ShopRes> res = shopService.search(shopSearchBO);
        return res;
    }


}
