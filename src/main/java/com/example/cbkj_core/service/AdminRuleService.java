package com.example.cbkj_core.service;

import com.example.cbkj_core.beans.AdminRule;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.AdminUtils;
import com.example.cbkj_core.common.IDUtil;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.mapper.AdminMenuMapper;
import com.example.cbkj_core.mapper.AdminRuleMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminRuleService {

    @Autowired
    private AdminRuleMapper adminRuleMapper;

    @Autowired
    private AdminMenuMapper adminMenuMapper;

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

    /**
     * 加载权限菜单
     * @param id
     * @return
     */
    public ResEntity findMenu(String id) throws Exception{
        List<Map<String,Object>> menuAll = adminMenuMapper.selectAllMenu();
        List<Map<String,Object>> ruleAllMenu = adminMenuMapper.getMenuByRid(id);
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> ruleM = new HashMap<>();
        Map<String,String> parentList = new HashMap<>();
        for(Map<String,Object> menu:ruleAllMenu){
            ruleM.put(menu.get("mid").toString(),menu.get("rmid"));
        }
        for(Map<String,Object> menu:menuAll){
            String parent = menu.get("parent_mid").toString();
            String value = menu.get("mid").toString();
            parentList.put(parent,value);
        }
        resultList = getChildrenMenu(menuAll,"0",ruleM,parentList);

        return new ResEntity(true,"SUCCESS",resultList);
    }

    public List<Map<String,Object>> getChildrenMenu(List<Map<String,Object>> lis,String parentID,Map<String,Object> ruleM,Map<String,String> parentList){
        List<Map<String,Object>> resultList = new ArrayList<>();
        for(Map<String,Object> menu:lis){
            if(null != menu){

                String pid = menu.get("parent_mid").toString();
                if(pid.equals(parentID)){
                    String title = menu.get("mname").toString();
                    String value = menu.get("mid").toString();
                    Map<String,Object> m = new HashMap<>();
                    m.put("title",title);
                    m.put("value",value);
                    if(ruleM.containsKey(value)){
                        m.put("checked",true);
                    }else{
                        m.put("checked",false);
                    }
                    List<Map<String,Object>> dataLis = new ArrayList<>();

                    if(parentList.containsKey(value)){
                        dataLis  = getChildrenMenu(lis,value,ruleM,parentList);
                    }
                    if(null != dataLis && dataLis.size()>0){
                        m.put("data",dataLis);
                    }
                    else{
                        m.put("data",dataLis);
                    }
                    resultList.add(m);
                }
            }

        }
        return resultList;
    }

    public ResEntity insertauthority(String mids, String rid) throws Exception{

        long rows = adminMenuMapper.deleteRmByRid(rid);
        if(!StringUtils.isBlank(mids)){
            List<Map<String,Object>> resultList = new ArrayList<>();
            for(String mid:mids.split(",")){
                Map<String,Object> m = new HashMap<>();
                m.put("rmid",IDUtil.getID());
                m.put("rid",rid);
                m.put("mid",mid);
                resultList.add(m);
            }
            long inserRows = adminMenuMapper.insertListM(resultList);
        }
        return new ResEntity(true,"SUCCESS",null);

    }
}
