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
import org.apache.commons.lang.StringUtils;
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

    /**
     * 加载分页数据
     * @param admin
     * @param page
     * @return
     */
    public Object getPageDatas(AdminInfo admin, Page page) {

        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Map<String,Object>> lis = adminInfoMapper.getPageDatas(admin);
        Object result = Page.getLayUiTablePageData(lis);

        return result;
    }

    /**
     * 插入新的管理员
     * @param adminInfo
     * @return
     * @throws Exception
     */
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
        return new ResEntity(false,"用户名不能重复哦",null);
    }

    /**
     * 获取当前系统所有角色
     * @return
     */
    public Object getRoles() {
        return adminInfoMapper.getRoles();
    }

    /**
     * 修改管理员
     * @param adminInfo
     * @return
     */
    public ResEntity update(AdminInfo adminInfo) {

        long rows = adminInfoMapper.updateByPrimaryKey(adminInfo);

        if(rows >0){
            return new ResEntity(true,"SUCCESS",null);
        }

        return new ResEntity(false,"用户名不能重复哦",null);
    }

    /**
     * 加载某个管理员
     * @param id
     * @return
     */
    public ResEntity findObj(String id) {

        if(StringUtils.isBlank(id)){
            return new ResEntity(false,"管理主键不能为空哦",null);
        }
        AdminInfo adminInfo = adminInfoMapper.selectByPrimaryKey(id);

        return new ResEntity(true,"SUCCESS",adminInfo);
    }

    /**
     * 修改/重置密码
     * @param ids
     * @param newPwd
     * @return
     */
    public ResEntity updatePwd(String ids, String newPwd) {

        if(StringUtils.isBlank(ids) || StringUtils.isBlank(newPwd)){
            return new ResEntity(false,"参数错误(缺少参数)！",null);
        }
        newPwd = MD5Util.encode(newPwd);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("newPwd",newPwd);
        params.put("ids", ids.split(","));

        long rows = adminInfoMapper.updatePwd(params);
        return new ResEntity(true,"SUCCESS",rows);
    }

    /**
     * 禁用/启用管理员
     * @param id
     * @param status
     * @return
     */
    public ResEntity updateStatus(String id, String status) {
        if(StringUtils.isBlank(id)||StringUtils.isBlank(status)){
            return new ResEntity(false,"参数错误(缺少参数)！",null);
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        params.put("status",status);
        long rows = adminInfoMapper.updateStatus(params);
        return new ResEntity(true,"SUCCESS",rows);
    }

    /**
     * 删除管理员
     * @param ids
     * @return
     */
    public ResEntity deleteLis(String ids)throws Exception {
        if(StringUtils.isBlank(ids)){
            return new ResEntity(false,"参数错误(缺少参数)！",null);
        }
        long rowsR = adminInfoMapper.deleteAdminRole(ids.split(","));
        long rowsA = 0;
        if(rowsR >0){
            rowsA = adminInfoMapper.deleteBylis(ids.split(","));
        }
        if(rowsA == rowsR){
            return new ResEntity(true,"SUCCESS",rowsA);
        }else{
            throw new Exception("删除数据不一致");
        }
    }
}
