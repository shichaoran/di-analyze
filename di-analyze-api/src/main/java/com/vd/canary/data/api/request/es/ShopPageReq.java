package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestPageBO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author shichaoran
 * @Date 2020/4/6 11:10
 * @Version
 */
@Getter
@Setter
@ToString
@Data
@Accessors(chain = true)
public class ShopPageReq extends RequestPageBO {
    private String key;
}
