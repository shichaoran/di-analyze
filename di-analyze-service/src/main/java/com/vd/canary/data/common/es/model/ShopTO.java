package com.vd.canary.data.common.es.model;

import com.vd.canary.data.api.response.es.ShopProductRes;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ShopTO implements Serializable {

    private String id; //店铺id
    private String name; //店铺名称
    private String boothCode;//展厅编号
    private String mediaUrl; //多媒体地址
    private String businessCategory; //经营类目
    private List<String> businessBrand;//品牌
    private String businessArea;//区域
    private String imageOrder;
    private String imageName; //名
    private String imageUrl; //地址
    private List<ShopProductRes> shopProductRes;
    private Map<String, String> classify;
    private String customerId;  // 客户·ID
    private String storeTemplateId; //模板id
    private String mainProducts;  //主营产品
    private String boothScheduledTime; //入驻时间

    public String getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(String mainProducts) {
        mainProducts = mainProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getBoothCode() {
        return boothCode;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getBusinessCategory() {
        return businessCategory;
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

    public ShopTO() {
    }

    public ShopTO(int id, String BoothCode, String mediaUrl, String businessCategory, String businessBrand, String businessArea, String imageOrder, String imageName, String imageUrl) {
        this.boothCode = boothCode;
        this.mediaUrl = mediaUrl;
        this.businessCategory = businessCategory;

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
        boothCode = boothCode;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public List<String> getBusinessBrand() {
        return businessBrand;
    }

    public void setBusinessBrand(List<String> businessBrand) {
        this.businessBrand = businessBrand;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public void setImageOrder(String imageOrder) {
        this.imageOrder = imageOrder;
    }

    public List<ShopProductRes> getShopProductRes() {
        return shopProductRes;
    }

    public void setShopProductRes(List<ShopProductRes> shopProductRes) {
        this.shopProductRes = shopProductRes;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    public String getBoothScheduledTime() {
        return boothScheduledTime;
    }

    public void setBoothScheduledTime(String boothScheduledTime) {
        this.boothScheduledTime = boothScheduledTime;
    }

    public void setClassify(Map<String, String> classify) {
        this.classify = classify;
    }
}