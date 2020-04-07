package com.vd.canary.data.api.response.es.vo;

import java.util.List;

/**
 * @Author shichaoran
 * @Date 2020/4/6 14:20
 * @Version
 */
public class ProductInfoVO {
    private int skuId;
    private String skuPic;
    private String skuTitle;
    private String priceJson;
    private String fThreeCategoty;

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public String getSkuPic() {
        return skuPic;
    }

    public void setSkuPic(String skuPic) {
        this.skuPic = skuPic;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public String getPriceJson() {
        return priceJson;
    }

    public void setPriceJson(String priceJson) {
        this.priceJson = priceJson;
    }

    public String getfThreeCategoty() {
        return fThreeCategoty;
    }

    public void setfThreeCategoty(String fThreeCategoty) {
        this.fThreeCategoty = fThreeCategoty;
    }
}
