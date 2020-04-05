package com.vd.canary.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vd.canary.business.repository.entity.BaseEntity;
import com.vd.canary.business.service.impl.BaseServiceImpl;
import com.vd.canary.core.bo.ResponsePageBO;
import com.vd.canary.core.util.PageResponseUtil;
import com.vd.canary.data.repository.entity.ESEntityWordEntity;
import com.vd.canary.data.repository.mapper.ESEntityWordMapper;
import com.vd.canary.data.service.ESEntityWordService;
import com.vd.canary.utils.BeanUtil;
import com.vd.canary.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vd.canary.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("esEntityWordService")
public class ESEntityWordServiceImpl extends BaseServiceImpl<ESEntityWordMapper, ESEntityWordEntity> implements ESEntityWordService {

    @Override
    public Boolean existEntityWord(String key) {
        return null;
    }
}
