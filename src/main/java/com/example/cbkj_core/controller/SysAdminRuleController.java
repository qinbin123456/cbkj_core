package com.example.cbkj_core.controller;

import com.example.cbkj_core.annotaionUtil.LogAnnotaion;
import com.example.cbkj_core.annotaionUtil.TokenAnnotaion;
import com.example.cbkj_core.beans.AdminRule;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.service.AdminRuleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysAdminRuleController{

    @Autowired
    private AdminRuleService adminRuleService;

    @RequestMapping("sys/rule")
    public String toIndex(){
        return "rule/index";
    }

    @RequestMapping("sys/rule/getPages")
    @ResponseBody
    public Object getApps(AdminRule adminRule, Page page) {
        Object obj = adminRuleService.getPageDatas(adminRule,page);
        return obj;
    }

    @RequestMapping("rule/insert/toPage")
    @TokenAnnotaion(toP = true)
    public String insertP(){

        return "rule/addOrUpdateP";
    }

    @RequestMapping("rule/authority/toPage")
    @TokenAnnotaion(toP = true)
    public String authorityP(String ID,Model model){
        model.addAttribute("id",ID);
        return "rule/authority";
    }

    @RequestMapping("rule/update/toPage")
    @TokenAnnotaion(toP = true)
    public String updateP(Model model, String ID){

        model.addAttribute("id",ID);
        return "rule/addOrUpdateP";
    }

    @RequestMapping("rule/update/findObj")
    @ResponseBody
    public Object getObj(String id){
        ResEntity result = adminRuleService.findObj(id);
        return result;
    }

    @RequestMapping("rule/insert")
    @LogAnnotaion(description = "新增角色")
    @TokenAnnotaion(submitP = true)
    @ResponseBody
    public Object insert(AdminRule adminRule) {
        ResEntity result = adminRuleService.insert(adminRule);
        return result;
    }



    @RequestMapping("rule/update")
    @LogAnnotaion(description = "修改角色")
    @TokenAnnotaion(submitP = true)
    @ResponseBody
    public Object update(AdminRule adminRule) {
        ResEntity result = adminRuleService.update(adminRule);
        return result;
    }

    @RequestMapping(value="rule/deleteLis")
    @LogAnnotaion(description = "删除角色")
    @ResponseBody
    public Object deleteLis(String ids){
        ResEntity result  = null;
        try {
            result = adminRuleService.deleteLis(ids);
        } catch (Exception e) {
            result = new ResEntity(false,"服务异常",null);
        }
        return result;
    }

    @RequestMapping("rule/authority/findMenu")
    @ResponseBody
    public Object findMenu(String id){
        ResEntity result = null;
        try {
            result = adminRuleService.findMenu(id);
            Object obj = result.getData();
            return obj;
        } catch (Exception e) {
            result = new ResEntity(false,"加载角色菜单异常",null);
        }
        return result;
    }

    @RequestMapping("rule/authority")
    @ResponseBody
    @LogAnnotaion(description = "权限设置")
    @TokenAnnotaion(submitP = true)
    public Object authority(String mids,String rid){
        ResEntity result = null;
        try {
            result = adminRuleService.insertauthority(mids,rid);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResEntity(false,"服务异常，请稍后重试",null);
        }
        return result;
    }
}
