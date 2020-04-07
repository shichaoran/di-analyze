package com.vd.canary.data.api.response.es.vo;

import java.io.Serializable;

public class CategoryVO implements Serializable {

    //前台一级分类id
    private Integer f_one_category_id;
    //前台一级分类code
    private String f_one_category_code;
    //前台一级分类
    private String f_one_category_name;
    //前台二级分类id
    private Integer f_two_category_id;
    //前台二级分类code
    private String f_two_category_code;
    //前台二级分类
    private String f_two_category_name;
    //前台三级分类id
    private Integer f_three_category_id;
    //前台三级分类code
    private String f_three_category_code;
    //前台三级分类
    private String f_three_category_name;


    public Integer getF_one_category_id() {
        return f_one_category_id;
    }

    public void setF_one_category_id(Integer f_one_category_id) {
        this.f_one_category_id = f_one_category_id;
    }

    public String getF_one_category_code() {
        return f_one_category_code;
    }

    public void setF_one_category_code(String f_one_category_code) {
        this.f_one_category_code = f_one_category_code;
    }

    public String getF_one_category_name() {
        return f_one_category_name;
    }

    public void setF_one_category_name(String f_one_category_name) {
        this.f_one_category_name = f_one_category_name;
    }

    public Integer getF_two_category_id() {
        return f_two_category_id;
    }

    public void setF_two_category_id(Integer f_two_category_id) {
        this.f_two_category_id = f_two_category_id;
    }

    public String getF_two_category_code() {
        return f_two_category_code;
    }

    public void setF_two_category_code(String f_two_category_code) {
        this.f_two_category_code = f_two_category_code;
    }

    public String getF_two_category_name() {
        return f_two_category_name;
    }

    public void setF_two_category_name(String f_two_category_name) {
        this.f_two_category_name = f_two_category_name;
    }

    public Integer getF_three_category_id() {
        return f_three_category_id;
    }

    public void setF_three_category_id(Integer f_three_category_id) {
        this.f_three_category_id = f_three_category_id;
    }

    public String getF_three_category_code() {
        return f_three_category_code;
    }

    public void setF_three_category_code(String f_three_category_code) {
        this.f_three_category_code = f_three_category_code;
    }

    public String getF_three_category_name() {
        return f_three_category_name;
    }

    public void setF_three_category_name(String f_three_category_name) {
        this.f_three_category_name = f_three_category_name;
    }

    @Override
    public String toString() {
        return "CategoryRes{" +
                "f_one_category_id=" + f_one_category_id +
                ", f_one_category_code='" + f_one_category_code + '\'' +
                ", f_one_category_name='" + f_one_category_name + '\'' +
                ", f_two_category_id=" + f_two_category_id +
                ", f_two_category_code='" + f_two_category_code + '\'' +
                ", f_two_category_name='" + f_two_category_name + '\'' +
                ", f_three_category_id=" + f_three_category_id +
                ", f_three_category_code='" + f_three_category_code + '\'' +
                ", f_three_category_name='" + f_three_category_name + '\'' +
                '}';
    }
}
