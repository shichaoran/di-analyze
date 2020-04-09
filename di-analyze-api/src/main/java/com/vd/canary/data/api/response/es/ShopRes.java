package com.vd.canary.data.api.response.es;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.management.relation.Role;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.List;
import java.util.Map;

/**
 * @Author shichaoran
 * @Date 2020/4/9 20:33
 * @Version
 */
@Data
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ShopRes implements Serializable {
    private String id; //店铺id
    private String BoothCode;//展厅编号
    private String mediaUrl; //多媒体地址
    private String businessCategory; //经营类目
    private String businessBrand;//品牌
    private String businessArea;//区域
    private String imageOrder;
    private String imageName; //名
    private String imageUrl; //地址
    private List<Role> roles;
    private Map<String, String> classify;
    private String customerId;  // 客户·ID
    private String storeTemplateId; //
    private String MainProducts;  //主营产品

}
