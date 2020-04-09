package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestBO;

import javax.validation.constraints.NotBlank;


public class OneCategoryReq extends RequestBO {

    /**
     * 商品一级目录id
     */
    @NotBlank(message = "id不能为空")
    private String fOneCategoryId;
}
