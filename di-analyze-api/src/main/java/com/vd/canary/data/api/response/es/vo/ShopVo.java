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
    private String Booth_code;//展厅编号
    private String media_url; //多媒体地址
    private String business_category; //经营类目
    private String business_brand;//品牌
    private String business_area;//区域
    private String image_order;
    private String image_name; //名
    private String image_url; //地址
    private List<Role> roles;
    private Map<String,String> classify;

    public ShopVo() {
    }
    public ShopVo(int id,String Booth_code,String media_url,String business_category,String business_brand,String business_area,String image_order,String image_name,String image_url){
        this.Booth_code = Booth_code;
        this.media_url = media_url;
        this.business_category = business_category;
        this.business_area = business_area;
        this.image_order = image_order;
        this.image_name = image_name;
        this.image_url = image_url;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBooth_code() {
        return Booth_code;
    }

    public void setBooth_code(String booth_code) {
        Booth_code = booth_code;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getBusiness_category() {
        return business_category;
    }

    public void setBusiness_category(String business_category) {
        this.business_category = business_category;
    }

    public String getBusiness_brand() {
        return business_brand;
    }

    public void setBusiness_brand(String business_brand) {
        this.business_brand = business_brand;
    }

    public String getBusiness_area() {
        return business_area;
    }

    public void setBusiness_area(String business_area) {
        this.business_area = business_area;
    }

    public String getImage_order() {
        return image_order;
    }

    public void setImage_order(String image_order) {
        this.image_order = image_order;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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