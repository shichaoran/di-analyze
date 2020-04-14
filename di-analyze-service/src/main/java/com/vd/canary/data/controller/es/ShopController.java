package com.vd.canary.data.controller.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.request.es.ShopSearchReq;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.api.response.es.ShopRes;
import com.vd.canary.data.service.es.ProductsService;
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
    private final ShopService shopService;


    @PostMapping("/shop/search")
    public ResponseBO<ShopRes> search(@RequestBody @Valid ShopSearchReq shopSearchBO) {
        ResponseBO<ShopRes> res = shopService.search(shopSearchBO);
        return res;
    }


}
