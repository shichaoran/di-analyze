package com.vd.canary.data.service.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.SearchShopReq;
import com.vd.canary.data.api.response.es.ShopRes;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:00
 * @Version
 */
public interface ShopService {


    ResponseBO<ShopRes> search(@RequestBody @Valid SearchShopReq shopSearchBO);
}
