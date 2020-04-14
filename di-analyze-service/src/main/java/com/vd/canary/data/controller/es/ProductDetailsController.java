package com.vd.canary.data.controller.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ProductDetailsReq;
import com.vd.canary.data.api.response.es.ProductDetailsRes;
import com.vd.canary.data.service.es.CategoryService;
import com.vd.canary.data.service.es.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:45
 * @Version
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductDetailsController {
    private final ProductDetailsService productDetailsService;

    @PostMapping("/products/getProductDetail")
    ResponseBO<ProductDetailsRes> getProductsDetail(@RequestBody @Valid ProductDetailsReq productDetailsReq) {
        ResponseBO<ProductDetailsRes> res =  productDetailsService.getProductsDetail(productDetailsReq);
        return res;
    }

}
