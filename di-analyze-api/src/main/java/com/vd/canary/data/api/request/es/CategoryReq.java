package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestBO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CategoryReq extends RequestBO {

    /**
     * 一组skuid
     */
    @NotBlank(message = "关键字不能为空")
    private List<String> skuIdList;
}
