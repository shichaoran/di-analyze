package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestPageBO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<String> bBrandId;
    /**
     * 三级类目名称
     */
    private List<String> fThreeCategoryName;
    /**
     * 所在地
     */
    private List<String> businessArea;

    /**
     * price为desc 或者 asc
     */
    private String priceSort;

    /**
     * 是否议价
     */
    private String isDiscussPrice;

    /**
     * 是否入驻
     */
    private String isHaveHouse;
}
