package com.vd.canary.data.repository.es.entity;

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
}
