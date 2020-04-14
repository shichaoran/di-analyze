package com.vd.canary.data.service.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.feign.es.ShopServiceFeign;
import com.vd.canary.data.api.request.es.CustomerReq;
import com.vd.canary.data.api.request.es.ProductListReq;
import com.vd.canary.data.api.request.es.ShopPageReq;
import com.vd.canary.data.api.request.es.ShopSearchReq;
import com.vd.canary.data.api.response.es.ShopProductRes;
import com.vd.canary.data.api.response.es.ShopRes;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:00
 * @Version
 */
public interface ShopService {


    ResponseBO<ShopRes> search(@RequestBody @Valid ShopSearchReq shopSearchBO);
}
