package com.vd.canary.data.api.response.es.vo;

import ch.qos.logback.core.boolex.EvaluationException;
import com.vd.canary.data.api.response.es.ShopRes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.HashSet;
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
    private String name; //店铺名称
    private String id; //店铺id
    private String boothCode;//展厅编号
    private String mediaUrl; //多媒体地址
    private String businessCategory; //经营类目
    private String businessBrand;//品牌
    private String businessArea;//区域
    private String imageOrder;
    private String imageName; //名
    private String imageUrl; //地址
    private List<ShopRes> shopRes;
    private HashSet<String> classify;
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

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public void setImageOrder(String imageOrder) {
        this.imageOrder = imageOrder;
    }

    public List<ShopRes> getShopRes() {
        return shopRes;
    }

    public void setShopRes(List<ShopRes> shopRes) {
        this.shopRes = shopRes;
    }




    public HashSet<String> getClassify() {
        return classify;
    }

    public void setClassify(HashSet<String> classify) {
        this.classify = classify;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getBusinessBrand() {
        return businessBrand;
    }

    public void setBusinessBrand(String businessBrand) {
        this.businessBrand = businessBrand;
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

}