package com.vd.canary.data.api.request;

import com.vd.canary.core.bo.RequestBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ESEntityWordReq extends RequestBO {

    @NotBlank(message = "关键字不能为空")
    private String key;

}
