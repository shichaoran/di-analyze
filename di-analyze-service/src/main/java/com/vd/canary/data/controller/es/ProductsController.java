package com.vd.canary.data.controller.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.service.es.ProductsService;
import com.vd.canary.service.controller.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vd.canary.service.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsController extends BaseController {

    private final ProductsService productsService;

    @PostMapping("/products/getProductsByKey")
    public ResponseBO<ProductsRes> getProductsByKey(@RequestBody @Valid ProductsReq productsReq){
        ResponseBO<ProductsRes> res = productsService.getProductsByKey(productsReq);
        return res;
    }

}
