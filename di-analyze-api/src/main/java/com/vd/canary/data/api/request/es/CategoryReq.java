package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestBO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Data
public class CategoryReq extends RequestBO {

    /**
     * 一组skuid
     */
    @NotEmpty(message = "关键字不能为空")
    private List<String> skuIdList;
}
