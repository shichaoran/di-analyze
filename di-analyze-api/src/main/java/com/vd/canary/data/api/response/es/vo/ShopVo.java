package com.vd.canary.data.api.response.es.vo;

import ch.qos.logback.core.boolex.EvaluationException;
import lombok.Data;
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

    public ShopVo() {
    }

    public ShopVo(int id, String Booth_code, String media_url, String business_category, String business_brand, String business_area, String image_order, String image_name, String image_url) {
        this.BoothCode = Booth_code;
        this.mediaUrl = media_url;
        this.businessCategory = business_category;
        this.businessArea = business_area;
        this.imageOrder = image_order;
        this.imageName = image_name;
        this.imageUrl = image_url;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoothCode() {
        return BoothCode;
    }

    public void setBoothCode(String boothCode) {
        BoothCode = boothCode;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getBusinessBrand() {
        return businessBrand;
    }

    public void setBusinessBrand(String businessBrand) {
        this.businessBrand = businessBrand;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(String imageOrder) {
        this.imageOrder = imageOrder;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Map getClassify() {
        return classify;
    }

    public void setClassify(Map classify) {
        this.classify = classify;
    }
}