package com.example.cbkj_core.mapper;

import com.example.cbkj_core.beans.AdminInfo;
import com.example.cbkj_core.beans.AdminInfoRule;

import java.util.List;
import java.util.Map;

public interface AdminInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(AdminInfo record);

    int insertSelective(AdminInfo record);

    AdminInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdminInfo record);

    int updateByPrimaryKey(AdminInfo record);

    AdminInfo loadUserByUsername(String username);

    List<Map<String,Object>> getPageDatas(AdminInfo admin);

    List<Map<String,Object>> getRoles();

    int insertAdminRule(AdminInfoRule adminInfoRule);

    long updatePwd(Map<String,Object> params);

    long updateStatus(Map<String,Object> params);

    long deleteAdminRole(String[] split);

    long deleteBylis(String[] split);
}