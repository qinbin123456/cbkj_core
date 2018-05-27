package com.example.cbkj_core.service;

import com.example.cbkj_core.beans.AdminInfo;
import com.example.cbkj_core.beans.AdminInfoRule;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.AdminUtils;
import com.example.cbkj_core.common.IDUtil;
import com.example.cbkj_core.common.MD5Util;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.mapper.AdminInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor=Exception.class)
public class AdminService implements UserDetailsService {

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

    public Object getPageDatas(AdminInfo admin, Page page) {

        Object result = null;
        PageHelper.startPage(page.getPage(), page.getLimit());
        try{
            List<Map<String,Object>> lis = adminInfoMapper.getPageDatas(admin);
            PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(lis);
            result = Page.getLayuiData(0,"success",pageInfo.getTotal(),lis);
        }catch (Exception e){
            result = Page.getLayuiData(1,"数据服务异常",0,null);
        }
        return result;
    }


    public ResEntity insert(AdminInfo adminInfo) throws Exception{
        adminInfo.setId(IDUtil.getID());
        adminInfo.setCreateId(AdminUtils.getCurrentHr().getId());
        adminInfo.setPassword(MD5Util.encode("123456"));//默认密码123456
        long rows = adminInfoMapper.insert(adminInfo);
        if(rows > 0){
            AdminInfoRule adminInfoRule = new AdminInfoRule();
            adminInfoRule.setIrID(IDUtil.getID());
            adminInfoRule.setAdminID(adminInfo.getId());
            adminInfoRule.setRid(adminInfo.getRid());
            adminInfoMapper.insertAdminRule(adminInfoRule);
            return new ResEntity(true,"SUCCESS",null);
        }
        return new ResEntity(false,"该用户已存在",null);
    }

    public Object getRoles() {
        return adminInfoMapper.getRoles();
    }
}
