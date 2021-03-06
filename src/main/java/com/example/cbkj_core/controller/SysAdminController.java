package com.example.cbkj_core.controller;

import com.example.cbkj_core.annotaionUtil.BtnAnnotaion;
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


    @RequestMapping("sys/admin")
    @BtnAnnotaion(btn = true)
    public String toIndex(){
        return "admin/index";
    }


    @RequestMapping("sys/admin/getPages")
    @ResponseBody
    public Object getApps(AdminInfo admin,Page page) {
        Object obj = adminService.getPageDatas(admin,page);
        return obj;
    }

    @RequestMapping("admin/insert/toPage")
    @TokenAnnotaion(toP = true)
    public String insertP(Model model){
        model.addAttribute("roles",adminService.getRoles());
        return "admin/addOrUpdateP";
    }

    @RequestMapping("admin/update/toPage")
    @TokenAnnotaion(toP = true)
    public String updateP(Model model, String ID){
        model.addAttribute("roles",adminService.getRoles());
        model.addAttribute("id",ID);
        return "admin/addOrUpdateP";
    }

    @RequestMapping("admin/update/findObj")
    @ResponseBody
    public Object getAdminInfo(String id){
        ResEntity result = adminService.findObj(id);
        return result;
    }

    @RequestMapping("admin/insert")
    @TokenAnnotaion(submitP = true,description = "新增管理员")
    @ResponseBody
    public Object insert(AdminInfo adminInfo) throws Exception {
        ResEntity result = adminService.insert(adminInfo);
        return result;
    }


    @RequestMapping("admin/update")
    @TokenAnnotaion(submitP = true,description = "修改管理员")
    @ResponseBody
    public Object update(AdminInfo adminInfo) {
        ResEntity result = adminService.update(adminInfo);
        return result;
    }

    @RequestMapping(value="admin/changePwd")
    @LogAnnotaion(description = "重置密码")
    @ResponseBody
    public Object changePwd(String ids,String newPwd){
        ResEntity result  = adminService.updatePwd(ids,newPwd);
        return result;
    }

    @RequestMapping(value="admin/changeStatus")
    @LogAnnotaion(description = "禁用启用管理员")
    @ResponseBody
    public Object changeStatus(String id,String status){
        ResEntity result  = adminService.updateStatus(id,status);
        return result;
    }

    @RequestMapping(value="admin/deleteLis")
    @LogAnnotaion(description = "删除管理员")
    @ResponseBody
    public Object deleteLis(String ids) throws Exception {
        ResEntity result   = adminService.deleteLis(ids);
        return result;
    }

}
