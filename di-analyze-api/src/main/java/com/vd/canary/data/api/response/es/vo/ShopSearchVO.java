package com.vd.canary.data.api.response.es.vo;

import com.vd.canary.data.api.response.es.ShopProductRes;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:06
 * @Version
 */
@Data
@ToString
@Accessors(chain = true)
public class ShopSearchVO implements Serializable {
    private String name; //店铺名称
    private String id; //店铺id
    private List<String> boothCode;//展厅编号
    private String mediaUrl; //多媒体地址
    private String businessCategory; //经营类目
    private List<String> businessBrand;//品牌
    private String businessArea;//区域
    private String imageOrder;
    private String imageName; //名
    private String imageUrl; //地址
    private List<ShopProductRes> shopProductRes;
    private HashSet<String> classify;
    private String customerId;  // 客户·ID
    private String storeTemplateId; //模板id
    private String mainProducts;  //主营产品
    private Date boothScheduledTime; //入驻时间
}
