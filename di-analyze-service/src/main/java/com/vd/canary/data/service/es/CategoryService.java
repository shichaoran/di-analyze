package com.vd.canary.data.service.es;

import com.vd.canary.core.bo.ResponseBO;
import com.vd.canary.data.api.request.es.CategoryReq;
import com.vd.canary.data.api.response.es.CategoryRes;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:41
 * @Version
 */
public interface CategoryService {
    ResponseBO<CategoryRes> categoryres(@RequestBody @Valid CategoryReq categoryReq);
}
