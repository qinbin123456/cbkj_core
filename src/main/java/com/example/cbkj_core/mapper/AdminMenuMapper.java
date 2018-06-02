package com.example.cbkj_core.mapper;

import com.example.cbkj_core.beans.AdminMenu;
import com.example.cbkj_core.beans.Menu;

import java.util.List;
import java.util.Map;

public interface AdminMenuMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(AdminMenu record);

    AdminMenu selectByPrimaryKey(Integer mid);

    int updateByPrimaryKey(AdminMenu record);

    List<AdminMenu> getAllMenu();

    List<Menu> getMenuByPID(Map<String,Object> params);

    List<Map<String,Object>> selectAllMenu();

    List<Map<String,Object>> getMenuByRid(String id);

    long deleteRmByRid(String rid);

    long insertListM(List<Map<String,Object>> resultList);

    List<Map<String,Object>> getPageDatas(AdminMenu adminMenu);

    List<Map<String,Object>> selectAllMenuByM();

    long updateEnabled(Map<String, Object> params);

    long deleteRMbyMid(String[] split);

    long deleteBylis(String[] split);

    List<Map<String,Object>> getBtnMenuLisByPath(Map<String,Object> params);
}