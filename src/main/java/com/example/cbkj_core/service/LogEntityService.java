package com.example.cbkj_core.service;

import com.example.cbkj_core.beans.LogEntity;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.AdminUtils;
import com.example.cbkj_core.common.IDUtil;
import com.example.cbkj_core.mapper.LogEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)
public class LogEntityService {

    @Autowired
    private LogEntityMapper logEntityMapper;

    public ResEntity insert(LogEntity logEntity){
        logEntity.setLid(IDUtil.getID());
        logEntity.setCreate_id(AdminUtils.getCurrentHr().getId());
        long rows = logEntityMapper.insert(logEntity);
        return new ResEntity(true,"SUCCESS",rows);
    }


}
