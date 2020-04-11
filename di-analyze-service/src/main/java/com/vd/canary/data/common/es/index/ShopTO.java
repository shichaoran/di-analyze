package com.vd.canary.data.common.es.index;

import com.vd.canary.data.api.response.es.ShopRes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "shopindex", type = "shop", shards = 5, replicas = 2)
public class ShopTO implements Serializable {
    @Id
    private String id; //店铺id
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name; //店铺名称
    private String boothCode;//展厅编号
    private String mediaUrl; //多媒体地址
    private String businessCategory; //经营类目
    private String businessBrand;//品牌
    private String businessArea;//区域
    private String imageOrder;
    private String imageName; //名
    private String imageUrl; //地址
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<ShopRes> shopRes;
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
        boothCode = boothCode;
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

    public List<ShopRes> getShopRes() {
        return shopRes;
    }

    public void setShopRes(List<ShopRes> shopRes) {
        this.shopRes = shopRes;
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