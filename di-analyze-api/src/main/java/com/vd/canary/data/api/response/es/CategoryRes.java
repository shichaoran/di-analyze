package com.vd.canary.data.api.response.es;

import com.vd.canary.data.api.response.es.vo.CategoryVO;

import java.io.Serializable;
import java.util.Map;

public class CategoryRes implements Serializable {

    private Integer sku_id;
    private CategoryVO categoryVO;
    private Map<Integer, CategoryVO> maplist;

    public Integer getSku_id() {
        return sku_id;
    }

    public void setSku_id(Integer sku_id) {
        this.sku_id = sku_id;
    }

    public CategoryVO getCategoryVO() {
        return categoryVO;
    }

    public void setCategoryVO(CategoryVO categoryVO) {
        this.categoryVO = categoryVO;
    }

    public Map<Integer, CategoryVO> getMaplist() {
        return maplist;
    }

    public void setMaplist(Map<Integer, CategoryVO> maplist) {
        this.maplist = maplist;
    }

    @Override
    public String toString() {
        return "CategoryRes{" +
                "sku_id=" + sku_id +
                ", categoryVO=" + categoryVO +
                ", maplist=" + maplist +
                '}';
    }
}