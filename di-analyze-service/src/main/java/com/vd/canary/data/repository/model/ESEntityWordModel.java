package com.vd.canary.data.repository.model;

import com.vd.canary.data.repository.mapper.ESEntityWordMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ESEntityWordModel {
    @Autowired
    private ESEntityWordMapper esEntityWordMapper;
}
