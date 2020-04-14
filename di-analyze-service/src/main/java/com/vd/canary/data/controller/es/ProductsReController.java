package com.vd.canary.data.controller.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ThreeCategoryReq;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.service.es.ProductDetailsService;
import com.vd.canary.data.service.es.ProductsReService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:54
 * @Version
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsReController {
    private final ProductsReService productsReService;

    @PostMapping("/products/getProductByCategory")
    ResponseBO<ProductsRes> getProductByCategory(@RequestBody @Valid ThreeCategoryReq threeCategoryReq) {
        ResponseBO<ProductsRes> res = productsReService.getProductByCategory(threeCategoryReq);
        return res;
    }

}
