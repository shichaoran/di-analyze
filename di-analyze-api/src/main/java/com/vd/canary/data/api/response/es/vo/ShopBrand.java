package com.vd.canary.data.api.response.es.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @Author shichaoran
 * @Date 2020/4/15 18:54
 * @Version
 */
@Data
public class ShopBrand implements Serializable {
    private String BrandId;
    private String BrandName;
}
