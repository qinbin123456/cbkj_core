package com.example.cbkj_core.service;

import com.example.cbkj_core.beans.AdminMenu;
import com.example.cbkj_core.mapper.AdminMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminMenuService {

    @Autowired
    private AdminMenuMapper adminMenuMapper;

    public List<AdminMenu> getAllMenu() {
        return adminMenuMapper.getAllMenu();
    }
}
