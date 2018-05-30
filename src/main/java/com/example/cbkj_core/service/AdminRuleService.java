package com.example.cbkj_core.service;

import com.example.cbkj_core.beans.AdminRule;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.AdminUtils;
import com.example.cbkj_core.common.IDUtil;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.mapper.AdminRuleMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminRuleService {

    @Autowired
    private AdminRuleMapper adminRuleMapper;

    /**
     * 加载分页数据
     * @param adminRule
     * @param page
     * @return
     */
    public Object getPageDatas(AdminRule adminRule, Page page) {

        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Map<String,Object>> lis = adminRuleMapper.getPageDatas(adminRule);
        Object result = Page.getLayUiTablePageData(lis);
        return result;

    }

    /**
     * 新增角色
     * @param adminRule
     * @return
     */
    public ResEntity insert(AdminRule adminRule) {
        adminRule.setRid(IDUtil.getID());
        adminRule.setAdminId(AdminUtils.getCurrentHr().getId());
        long rows = adminRuleMapper.insert(adminRule);
        if(rows>0){
            return new ResEntity(true,"SUCCESS",rows);
        }
        return new ResEntity(false,"数据库异常",null);
    }

    /**
     * 获取某个角色
     * @param id
     * @return
     */
    public ResEntity findObj(String id) {
        if(StringUtils.isBlank(id)){
            return new ResEntity(false,"缺少参数！",null);
        }
        Object obj = adminRuleMapper.selectByPrimaryKey(id);
        return new ResEntity(true,"SUCCESS",obj);
    }

    /**
     * 修改角色
     * @param adminRule
     * @return
     */
    public ResEntity update(AdminRule adminRule) {
        if(StringUtils.isBlank(adminRule.getRid())){
            return new ResEntity(false,"缺少参数！",null);
        }
        long rows = adminRuleMapper.updateByPrimaryKey(adminRule);
        return new ResEntity(true,"SUCCESS",rows);
    }

    /**
     * 删除角色
     * @param ids
     * @return
     */
    public ResEntity deleteLis(String ids) throws Exception{
        if(StringUtils.isBlank(ids)){
            return new ResEntity(false,"缺少参数！",null);
        }
        long rows = adminRuleMapper.getRuleRelesCount(ids);
        if(rows > 0){
            return new ResEntity(false,"当前角色正在被使用，不能删除",null);
        }else{
            long rowA = adminRuleMapper.deleteByPrimaryKey(ids);
            long rowB = adminRuleMapper.deleteRuleMenuByRid(ids);
            return new ResEntity(true,"SUCCESS",rowA);
        }
    }
}
