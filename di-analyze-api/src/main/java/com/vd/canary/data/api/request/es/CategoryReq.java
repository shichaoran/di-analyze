package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestPageBO;

import javax.validation.constraints.NotBlank;

public class CategoryReq extends RequestPageBO {

    @NotBlank(message = "关键字不能为空")
    private Integer sku_id;
}
