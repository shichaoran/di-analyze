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

    public String getMainProducts() {
        return MainProducts;
    }

    public void setMainProducts(String mainProducts) {
        MainProducts = mainProducts;
    }

    public String getId() {
        return id;
    }

    public String getBoothCode() {
        return BoothCode;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public String getBusinessBrand() {
        return businessBrand;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public String getImageOrder() {
        return imageOrder;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Map<String, String> getClassify() {
        return classify;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStoreTemplateId() {
        return storeTemplateId;
    }

    public void setStoreTemplateId(String storeTemplateId) {
        this.storeTemplateId = storeTemplateId;
    }

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

    public void setCustomerId(String customerId){
        customerId = customerId;
    }
    public void setId(String id) {
        id = id;
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