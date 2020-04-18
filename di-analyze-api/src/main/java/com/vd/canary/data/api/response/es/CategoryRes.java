package com.vd.canary.data.api.response.es;

import com.vd.canary.data.api.response.es.vo.CategoryVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ToString
public class CategoryRes implements Serializable {
    //skuid
    //private String skuId;
    //类目
    //private CategoryVO categoryVO;

    private Map<String, CategoryVO> maplist;

    //
    //public void setMaplist(String s, CategoryVO categoryVO) {
    //}
}
