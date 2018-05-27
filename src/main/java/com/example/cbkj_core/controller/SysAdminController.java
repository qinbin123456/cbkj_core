package com.example.cbkj_core.controller;

import com.example.cbkj_core.beans.AdminInfo;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.service.AdminService;
import com.example.cbkj_core.annotaionUtil.LogAnnotaion;
import com.example.cbkj_core.annotaionUtil.TokenAnnotaion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SysAdminController {

    @Autowired
    private AdminService adminService;


    @RequestMapping("sys/admin/index")
    public String toIndex(){
       return "admin/index";
    }


    @RequestMapping("sys/admin/getPages")
    @ResponseBody
    public Object getApps(AdminInfo admin,Page page) {
        Object obj = adminService.getPageDatas(admin,page);
        return obj;
    }

    @RequestMapping("admin/edit/toPage")
    @TokenAnnotaion(toP = true)
    public String addOrUpdateP(Model model, String ID){
        model.addAttribute("roles",adminService.getRoles());
        model.addAttribute("id",ID);
        return "admin/addOrUpdateP";
    }

    @RequestMapping("admin/edit/insert")
    @LogAnnotaion(description = "新增管理员")
    @TokenAnnotaion(submitP = true)
    @ResponseBody
    public Object insert(AdminInfo adminInfo) {
        ResEntity result = null;
        try {
            result = adminService.insert(adminInfo);
        } catch (Exception e) {
            result = new ResEntity(false,"服务异常",null);
        }
        return result;
    }



}
