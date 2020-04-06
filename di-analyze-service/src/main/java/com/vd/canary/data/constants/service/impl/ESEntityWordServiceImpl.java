package com.vd.canary.data.constants.service.impl;

import com.vd.canary.business.service.impl.BaseServiceImpl;
import com.vd.canary.data.repository.entity.ESEntityWordEntity;
import com.vd.canary.data.repository.mapper.ESEntityWordMapper;
import com.vd.canary.data.constants.service.ESEntityWordService;

import org.springframework.stereotype.Service;

@Service("esEntityWordService")
public class ESEntityWordServiceImpl extends BaseServiceImpl<ESEntityWordMapper, ESEntityWordEntity> implements ESEntityWordService {

    @Override
    public Boolean existEntityWord(String key) {
        return null;
    }
}
