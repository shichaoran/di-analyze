package com.vd.canary.data.api.response.es.vo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString
@Accessors(chain = true)
public class ESEntityWordVO implements Serializable {
    /**
     * account_id 主键ID
     */

    private String accountId;
}
