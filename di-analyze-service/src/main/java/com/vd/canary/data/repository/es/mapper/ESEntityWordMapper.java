package com.vd.canary.data.repository.es.mapper;

import com.vd.canary.business.repository.mapper.BaseMapper;
import com.vd.canary.data.repository.es.entity.ESEntityWordEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ESEntityWordMapper extends BaseMapper<ESEntityWordEntity> {
}
