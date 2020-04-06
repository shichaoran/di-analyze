package com.vd.canary.data.api.response.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ESEntityWordRes implements Serializable{

    private static final long serialVersionUID = -6115431577594402479L;
    /**
     * 关键字key
     */
    private String key;

    /**
     * 关键字value
     */
    private String value;

    /**
     * 创建时间
     */
    private Date createTime;


}
