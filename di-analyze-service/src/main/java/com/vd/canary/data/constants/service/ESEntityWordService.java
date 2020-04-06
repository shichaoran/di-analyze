package com.vd.canary.data.constants.service;

import com.vd.canary.business.service.BaseService;
import com.vd.canary.data.repository.entity.ESEntityWordEntity;


public interface ESEntityWordService extends BaseService<ESEntityWordEntity> {

    Boolean existEntityWord(String key);

}
