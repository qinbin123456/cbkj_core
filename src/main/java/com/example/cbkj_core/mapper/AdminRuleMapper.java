package com.example.cbkj_core.mapper;

import com.example.cbkj_core.beans.AdminRule;

public interface AdminRuleMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(AdminRule record);

    int insertSelective(AdminRule record);

    AdminRule selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(AdminRule record);

    int updateByPrimaryKey(AdminRule record);
}