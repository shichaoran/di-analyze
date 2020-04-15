package com.vd.canary.data.api.response.es;

import com.vd.canary.data.api.response.es.vo.ShopSearchVO;
import com.vd.canary.data.api.response.es.vo.ShopVo;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

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

    private List<ShopVo> shopVos;
    private List<ShopProductRes> shopProductRes;
    private List<ShopSearchVO> shopSeatchVOS;
    private int total;
}
