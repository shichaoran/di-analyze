package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestBO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author shichaoran
 * @Date 2020/4/9 13:31
 * @Version
 */

@Getter
@Setter
@ToString
@Data
@Accessors(chain = true)
public class ShopSearchBO  extends RequestBO {
    private String key;
}