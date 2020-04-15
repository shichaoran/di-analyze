package com.vd.canary.data.api.request.es;

import com.vd.canary.core.bo.RequestBO;
import com.vd.canary.core.bo.RequestPageBO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author shichaoran
 * @Date 2020/4/9 13:31
 * @Version
 */

@Getter
@Setter
@ToString
@Data
@Accessors(chain = true)
public class SearchShopReq extends RequestPageBO {
    private List<String> categoryIds;
    private List<String> brandIds;
    private boolean exhibitionJoined;
    private String key;
}