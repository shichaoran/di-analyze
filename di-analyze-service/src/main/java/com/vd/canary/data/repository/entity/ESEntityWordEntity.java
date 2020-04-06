package com.vd.canary.data.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.vd.canary.business.repository.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("es_entityword")
public class ESEntityWordEntity extends BaseEntity<ESEntityWordEntity>{
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("entityword")
    private String entityword;

    @TableField("remark")
    private String remark;
}
