package com.vd.canary.data.api.response.es.vo;

import java.util.List;

/**
 * @Author shichaoran
 * @Date 2020/4/6 14:20
 * @Version
 */
public class ProductInfoVO {
    private int skuid;
    private String  sku_pic;
    private String  sku_title;
    private String  price_json;
    private String f_three_categoty;
    private List    wangle;

    public int getSkuid() {
        return skuid;
    }

    public void setSkuid(int skuid) {
        this.skuid = skuid;
    }

    public String getSku_pic() {
        return sku_pic;
    }

    public void setSku_pic(String sku_pic) {
        this.sku_pic = sku_pic;
    }

    public String getSku_title() {
        return sku_title;
    }

    public void setSku_title(String sku_title) {
        this.sku_title = sku_title;
    }

    public String getPrice_json() {
        return price_json;
    }

    public void setPrice_json(String price_json) {
        this.price_json = price_json;
    }

    public String getF_three_categoty() {
        return f_three_categoty;
    }

    public void setF_three_categoty(String f_three_categoty) {
        this.f_three_categoty = f_three_categoty;
    }

    public List getWangle() {
        return wangle;
    }

    public void setWangle(List wangle) {
        this.wangle = wangle;
    }
}
