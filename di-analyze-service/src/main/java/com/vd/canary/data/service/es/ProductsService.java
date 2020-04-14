package com.vd.canary.data.service.es;

import com.vd.canary.business.service.BaseService;
import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.data.api.request.es.ProductsReq;
import com.vd.canary.data.api.response.es.ProductsRes;
import com.vd.canary.data.repository.es.entity.ESEntityWordEntity;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ProductsService  {

    ResponseBO<ProductsRes> getProductsByKey(@RequestBody @Valid ProductsReq productsReq);
}
