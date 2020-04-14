package com.vd.canary.data.service.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.response.es.ProductsRes;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:54
 * @Version
 */
public interface ProductsReService {
    ResponseBO<ProductsRes> getProductByCategory(@RequestBody ProductsReq threeCategoryReq);
}
