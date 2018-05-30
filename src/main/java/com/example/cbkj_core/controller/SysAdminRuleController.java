package com.example.cbkj_core.controller;

import com.example.cbkj_core.annotaionUtil.LogAnnotaion;
import com.example.cbkj_core.annotaionUtil.TokenAnnotaion;
import com.example.cbkj_core.beans.AdminRule;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.service.AdminRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysAdminRuleController{

    @Autowired
    private AdminRuleService adminRuleService;

    @RequestMapping("sys/rule/index")
    public String toIndex(){
        return "rule/index";
    }

    @RequestMapping("sys/rule/getPages")
    @ResponseBody
    public Object getApps(AdminRule adminRule, Page page) {
        Object obj = adminRuleService.getPageDatas(adminRule,page);
        return obj;
    }

    @RequestMapping("rule/edit/toPage")
    @TokenAnnotaion(toP = true)
    public String addOrUpdateP(Model model, String ID){
        model.addAttribute("id",ID);
        return "rule/addOrUpdateP";
    }

    @RequestMapping("rule/edit/insert")
    @LogAnnotaion(description = "新增角色")
    @TokenAnnotaion(submitP = true)
    @ResponseBody
    public Object insert(AdminRule adminRule) {
        ResEntity result = adminRuleService.insert(adminRule);
        return result;
    }

    @RequestMapping("rule/edit/findObj")
    @ResponseBody
    @LogAnnotaion(description = "获取修改角色的数据")
    public Object getObj(String id){
        ResEntity result = adminRuleService.findObj(id);
        return result;
    }

    @RequestMapping("rule/edit/update")
    @LogAnnotaion(description = "修改角色")
    @TokenAnnotaion(submitP = true)
    @ResponseBody
    public Object update(AdminRule adminRule) {
        ResEntity result = adminRuleService.update(adminRule);
        return result;
    }

    @RequestMapping(value="rule/edit/deleteLis")
    @LogAnnotaion(description = "删除角色")
    @ResponseBody
    public Object deleteLis(String ids){
        ResEntity result  = null;
        try {
            result = adminRuleService.deleteLis(ids);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResEntity(false,"服务异常",null);
        }

        return result;
    }

}
