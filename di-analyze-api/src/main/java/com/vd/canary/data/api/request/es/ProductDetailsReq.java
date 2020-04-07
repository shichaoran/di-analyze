package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsReq extends RequestBO {
    @NotBlank(message = "id不能为空")
    private Integer productId;
}
