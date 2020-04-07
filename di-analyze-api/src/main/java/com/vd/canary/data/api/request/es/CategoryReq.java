package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestPageBO;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CategoryReq extends RequestPageBO {

    @NotBlank(message = "关键字不能为空")
    private List skuIdList;
}
