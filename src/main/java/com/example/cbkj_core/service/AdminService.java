package com.example.cbkj_core.service;

import com.example.cbkj_core.beans.AdminInfo;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.mapper.AdminInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminService implements UserDetailsService {

    private final Logger looger = LoggerFactory.getLogger(AdminService.class);
    @Autowired
    private AdminInfoMapper adminInfoMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminInfo admin = adminInfoMapper.loadUserByUsername(username);
        if (null == admin) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return admin;
    }

    /**
     * 分页数据
     * @param admin
     * @param page
     * @return
     */
    public Object getPageDatas(AdminInfo admin, Page page) {

        Object result = null;
        PageHelper.startPage(page.getPage(), page.getLimit());
        try{

            List<Map<String,Object>> lis = adminInfoMapper.getPageDatas(admin);
            PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(lis);
            result = Page.getLayuiData(0,"success",pageInfo.getTotal(),lis);

        }catch (Exception e){

            result = Page.getLayuiData(1,"数据服务异常",0,null);
            looger.error(getClass().getPackage().getName(),e);
        }
        return result;
    }

    public ResEntity insert(AdminInfo adminInfo) {
        System.out.println(adminInfo.getName());
        return new ResEntity("error","系统异常，测试重复提交",null);
    }
}
