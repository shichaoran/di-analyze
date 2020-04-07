package com.vd.canary.data.api.response.es.vo;

import java.io.Serializable;

public class CategoryVO implements Serializable {

    //前台一级分类id
    private Integer fOneCategoryId;
    //前台一级分类code
    private String fOneCategoryCode;
    //前台一级分类
    private String fOneCategoryName;
    //前台二级分类id
    private Integer fTwoCategoryId;
    //前台二级分类code
    private String fTwoCategoryCode;
    //前台二级分类
    private String fTwoCategoryName;
    //前台三级分类id
    private Integer fThreeCategoryId;
    //前台三级分类code
    private String fThreeCategoryCode;
    //前台三级分类
    private String fThreeCategoryName;


    public Integer getfOneCategoryId() {
        return fOneCategoryId;
    }

    public void setfOneCategoryId(Integer fOneCategoryId) {
        this.fOneCategoryId = fOneCategoryId;
    }

    public String getfOneCategoryCode() {
        return fOneCategoryCode;
    }

    public void setfOneCategoryCode(String fOneCategoryCode) {
        this.fOneCategoryCode = fOneCategoryCode;
    }

    public String getfOneCategoryName() {
        return fOneCategoryName;
    }

    public void setfOneCategoryName(String fOneCategoryName) {
        this.fOneCategoryName = fOneCategoryName;
    }

    public Integer getfTwoCategoryId() {
        return fTwoCategoryId;
    }

    public void setfTwoCategoryId(Integer fTwoCategoryId) {
        this.fTwoCategoryId = fTwoCategoryId;
    }

    public String getfTwoCategoryCode() {
        return fTwoCategoryCode;
    }

    public void setfTwoCategoryCode(String fTwoCategoryCode) {
        this.fTwoCategoryCode = fTwoCategoryCode;
    }

    public String getfTwoCategoryName() {
        return fTwoCategoryName;
    }

    public void setfTwoCategoryName(String fTwoCategoryName) {
        this.fTwoCategoryName = fTwoCategoryName;
    }

    public Integer getfThreeCategoryId() {
        return fThreeCategoryId;
    }

    public void setfThreeCategoryId(Integer fThreeCategoryId) {
        this.fThreeCategoryId = fThreeCategoryId;
    }

    public String getfThreeCategoryCode() {
        return fThreeCategoryCode;
    }

    public void setfThreeCategoryCode(String fThreeCategoryCode) {
        this.fThreeCategoryCode = fThreeCategoryCode;
    }

    public String getfThreeCategoryName() {
        return fThreeCategoryName;
    }

    public void setfThreeCategoryName(String fThreeCategoryName) {
        this.fThreeCategoryName = fThreeCategoryName;
    }

    @Override
    public String toString() {
        return "CategoryVO{" +
                "fOneCategoryId=" + fOneCategoryId +
                ", fOneCategoryCode='" + fOneCategoryCode + '\'' +
                ", fOneCategoryName='" + fOneCategoryName + '\'' +
                ", fTwoCategoryId=" + fTwoCategoryId +
                ", fTwoCategoryCode='" + fTwoCategoryCode + '\'' +
                ", fTwoCategoryName='" + fTwoCategoryName + '\'' +
                ", fThreeCategoryId=" + fThreeCategoryId +
                ", fThreeCategoryCode='" + fThreeCategoryCode + '\'' +
                ", fThreeCategoryName='" + fThreeCategoryName + '\'' +
                '}';
    }
}
