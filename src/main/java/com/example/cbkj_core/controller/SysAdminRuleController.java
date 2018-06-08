package com.example.cbkj_core.controller;

import com.example.cbkj_core.annotaionUtil.BtnAnnotaion;
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
    @BtnAnnotaion(btn = true)
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
    @TokenAnnotaion(submitP = true,description = "新增角色")
    @ResponseBody
    public Object insert(AdminRule adminRule) {
        ResEntity result = adminRuleService.insert(adminRule);
        return result;
    }

    @RequestMapping("rule/update")
    @TokenAnnotaion(submitP = true,description = "修改角色")
    @ResponseBody
    public Object update(AdminRule adminRule) {
        ResEntity result = adminRuleService.update(adminRule);
        return result;
    }

    @RequestMapping(value="rule/deleteLis")
    @LogAnnotaion(description = "删除角色")
    @ResponseBody
    public Object deleteLis(String ids) throws Exception {
        ResEntity result = adminRuleService.deleteLis(ids);

        return result;
    }

    @RequestMapping("rule/authority/findMenu")
    @LogAnnotaion(description = "加载角色菜单")
    @ResponseBody
    public Object findMenu(String id) throws Exception {
        ResEntity result = adminRuleService.findMenu(id);
        return result;
    }

    @RequestMapping("rule/authority")
    @ResponseBody
    @LogAnnotaion(description = "权限设置")
    @TokenAnnotaion(submitP = true)
    public Object authority(String mids,String rid) throws Exception {
        ResEntity result = adminRuleService.insertauthority(mids,rid);
        return result;
    }
}
