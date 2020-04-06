package com.vd.canary.data.service.es;

import com.vd.canary.business.service.BaseService;
import com.vd.canary.data.repository.es.entity.ESEntityWordEntity;


public interface ESEntityWordService extends BaseService<ESEntityWordEntity> {

    Boolean existEntityWord(String key);

}
