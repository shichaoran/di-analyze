package com.vd.canary.data.service;

import com.vd.canary.business.service.BaseService;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.data.repository.entity.ESEntityWordEntity;

import java.util.List;


public interface ESEntityWordService extends BaseService<ESEntityWordEntity> {

    Boolean existEntityWord(String key);

}
