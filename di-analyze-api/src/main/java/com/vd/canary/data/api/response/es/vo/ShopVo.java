package com.vd.canary.data.api.response.es.vo;

import ch.qos.logback.core.boolex.EvaluationException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author shichaoran
 * @Date 2020/4/6 10:37
 * @Version
 */
@Data
@Getter
@Setter
@ToString
@Accessors(chain = true)

public class ShopVo implements Serializable {
    private int id; //店铺id
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
    public ShopVo() {
    }

    public ShopVo(int id, String BoothCode, String mediaUrl, String businessCategory, String businessBrand, String businessArea, String imageOrder, String imageName, String imageUrl) {
        this.BoothCode = BoothCode;
        this.mediaUrl = mediaUrl;
        this.businessCategory = businessCategory;
        this.businessBrand = businessBrand;
        this.businessArea = businessArea;
        this.imageOrder = imageOrder;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBoothCode(String boothCode) {
        BoothCode = boothCode;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public void setBusinessBrand(String businessBrand) {
        this.businessBrand = businessBrand;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public void setImageOrder(String imageOrder) {
        this.imageOrder = imageOrder;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setClassify(Map<String, String> classify) {
        this.classify = classify;
    }
}