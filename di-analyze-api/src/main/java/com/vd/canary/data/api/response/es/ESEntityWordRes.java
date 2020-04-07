package com.vd.canary.data.api.response.es;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
