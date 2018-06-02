package com.example.cbkj_core.mapper;

import com.example.cbkj_core.beans.AdminRule;

import java.util.List;
import java.util.Map;

public interface AdminRuleMapper {
    int deleteByPrimaryKey(String rid);

    int insert(AdminRule record);

    int insertSelective(AdminRule record);

    AdminRule selectByPrimaryKey(String rid);

    int updateByPrimaryKeySelective(AdminRule record);

    int updateByPrimaryKey(AdminRule record);

    List<Map<String,Object>> getPageDatas(AdminRule adminRule);

    long getRuleRelesCount(String ids);

    long deleteRuleMenuByRid(String ids);

    long deleteRuleMenuByRids(String ids);
}