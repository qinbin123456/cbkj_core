package com.example.cbkj_core.mapper;

import com.example.cbkj_core.beans.AdminMenu;

import java.util.List;

public interface AdminMenuMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(AdminMenu record);

    int insertSelective(AdminMenu record);

    AdminMenu selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(AdminMenu record);

    int updateByPrimaryKey(AdminMenu record);

    List<AdminMenu> getAllMenu();
}