package com.vd.canary.data.service.es.impl;

import com.vd.canary.business.service.impl.BaseServiceImpl;
import com.vd.canary.data.repository.es.entity.ESEntityWordEntity;
import com.vd.canary.data.repository.es.mapper.ESEntityWordMapper;
import com.vd.canary.data.service.es.ESEntityWordService;

import org.springframework.stereotype.Service;

@Service("esEntityWordService")
public class ESEntityWordServiceImpl extends BaseServiceImpl<ESEntityWordMapper, ESEntityWordEntity> implements ESEntityWordService {

    @Override
    public Boolean existEntityWord(String key) {
        return null;
    }
}
