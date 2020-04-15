package com.vd.canary.data.api.response.es;

import com.vd.canary.data.api.response.es.vo.ShopBrand;

import com.vd.canary.data.api.response.es.vo.ShopVo;

import com.vd.canary.data.api.response.es.vo.ShopThreeCategory;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author shichaoran
 * @Date 2020/4/14 16:03
 * @Version
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ShopRes implements Serializable {
    private static final long serialVersionUID = 3799924045437177866L;
    private List<ShopVo> shopVos;
    private Map<String,String> brands;
    private Map<String,String> categories;
    private int total;
}
