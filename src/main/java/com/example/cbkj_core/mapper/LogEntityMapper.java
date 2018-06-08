package com.example.cbkj_core.mapper;

import com.example.cbkj_core.beans.AdminRule;
import com.example.cbkj_core.beans.LogEntity;

import java.util.List;
import java.util.Map;

public interface LogEntityMapper {

    List<Map<String,Object>> getPageDatas(LogEntity logEntity);

    int insert(LogEntity logEntity);

    int deleteRuleMenuByRids(String ids);
}
