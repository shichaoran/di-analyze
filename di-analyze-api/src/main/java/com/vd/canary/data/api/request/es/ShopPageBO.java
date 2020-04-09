package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestPageBO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author shichaoran
 * @Date 2020/4/6 11:10
 * @Version
 */
@Data
@Accessors(chain = true)
public class ShopPageBO extends RequestPageBO {
    private String key;
}