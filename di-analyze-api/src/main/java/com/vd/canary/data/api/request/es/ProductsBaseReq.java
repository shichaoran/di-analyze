package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestPageBO;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author WangRuilin
 * @Date 2020/4/7 13:54
 */
@Setter
@Getter
public class ProductsBaseReq extends RequestPageBO  {
    /**
     * 品牌id
     */
    private String bBrandId;
    /**
     * 三级类目名称
     */
    private String fThreeCategoryName;
    /**
     * 所在地
     */
    private String businessArea;
}
