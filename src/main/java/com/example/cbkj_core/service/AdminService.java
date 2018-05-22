package com.example.cbkj_core.service;

import com.example.cbkj_core.beans.AdminInfo;
import com.example.cbkj_core.mapper.AdminInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminInfoMapper adminInfoMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminInfo admin = adminInfoMapper.loadUserByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return admin;
    }

    public List<Map<String,Object>> getPageDatas(AdminInfo admin) {

        return adminInfoMapper.getPageDatas(admin);
    }
}
