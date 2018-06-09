package com.example.cbkj_core.controller;

import com.example.cbkj_core.annotaionUtil.BtnAnnotaion;
import com.example.cbkj_core.annotaionUtil.LogAnnotaion;
import com.example.cbkj_core.annotaionUtil.TokenAnnotaion;
import com.example.cbkj_core.beans.AdminMenu;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysMenuController {

    @Autowired
    private AdminMenuService adminMenuService;

    @RequestMapping("sys/menu")
    @BtnAnnotaion(btn = true)
    public String toIndex(){
        return "menu/index";
    }

    @RequestMapping("sys/menu/getPages")
    @ResponseBody
    public Object getApps(AdminMenu adminMenu, Page page) {
        Object obj = adminMenuService.getPageDatas(adminMenu,page);
        return obj;
    }

    @RequestMapping("menu/insert/toPage")
    @TokenAnnotaion(toP = true)
    public String insertP(Model model){
        model.addAttribute("menulis",adminMenuService.getAllMenuListM());
        return "menu/addOrUpdateP";
    }

    @RequestMapping("menu/update/toPage")
    @TokenAnnotaion(toP = true)
    public String updateP(Model model, String ID){
        model.addAttribute("menulis",adminMenuService.getAllMenuListM());
        model.addAttribute("mid",ID);
        return "menu/addOrUpdateP";
    }

    @RequestMapping("menu/iconP")
    public String iconP(){
        return "menu/iconP";
    }

    @RequestMapping("menu/btnP")
    public String btnP(){
        return "menu/btnP";
    }

    @RequestMapping("menu/update/findObj")
    @ResponseBody
    public Object getObj(String id){
        ResEntity result = adminMenuService.findObj(id);
        return result;
    }

    @RequestMapping("menu/insert")
    @LogAnnotaion(description = "新增菜单")
    @TokenAnnotaion(submitP = true)
    @ResponseBody
    public Object insert(AdminMenu adminMenu) {
        ResEntity result = adminMenuService.insert(adminMenu);
        return result;
    }

    @RequestMapping("menu/update")
    @LogAnnotaion(description = "修改菜单")
    @TokenAnnotaion(submitP = true)
    @ResponseBody
    public Object update(AdminMenu adminMenu) {
        ResEntity result = adminMenuService.update(adminMenu);
        return result;
    }

    @RequestMapping(value="menu/deleteLis")
    @LogAnnotaion(description = "删除菜单")
    @ResponseBody
    public Object deleteLis(String ids) throws Exception {
        ResEntity result   = adminMenuService.deleteLis(ids);
        return result;
    }

    @RequestMapping(value="menu/changeEnabled")
    @LogAnnotaion(description = "禁用启用菜单")
    @ResponseBody
    public Object changeEnabled(String mid,Integer enabled){
        ResEntity result  = adminMenuService.updateEnableds(mid,enabled);
        return result;
    }


}
