package com.example.cbkj_core.mapper;

import com.example.cbkj_core.beans.AdminInfo;

import java.util.List;
import java.util.Map;

public interface AdminInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminInfo record);

    int insertSelective(AdminInfo record);

    AdminInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminInfo record);

    int updateByPrimaryKey(AdminInfo record);

    AdminInfo loadUserByUsername(String username);

    List<Map<String,Object>> getPageDatas(AdminInfo admin);
}