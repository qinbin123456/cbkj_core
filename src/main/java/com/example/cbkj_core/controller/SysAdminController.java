package com.example.cbkj_core.controller;

import com.example.cbkj_core.beans.AdminInfo;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.service.AdminService;
import com.example.cbkj_core.annotaionUtil.LogAnnotaion;
import com.example.cbkj_core.annotaionUtil.TokenAnnotaion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

    @RequestMapping("admin/edit/findObj")
    @ResponseBody
    @LogAnnotaion(description = "获取修改管理员的数据")
    public Object getAdminInfo(String id){
        ResEntity result = adminService.findObj(id);
        return result;
    }

    @RequestMapping("admin/edit/update")
    @LogAnnotaion(description = "修改管理员")
    @TokenAnnotaion(submitP = true)
    @ResponseBody
    public Object update(AdminInfo adminInfo) {
        ResEntity result = adminService.update(adminInfo);
        return result;
    }

    @RequestMapping(value="admin/edit/changePwd")
    @LogAnnotaion(description = "重置密码")
    @ResponseBody
    public Object changePwd(String ids,String newPwd){
        ResEntity result  = adminService.updatePwd(ids,newPwd);
        return result;
    }

    @RequestMapping(value="admin/edit/changeStatus")
    @LogAnnotaion(description = "禁用启用管理员")
    @ResponseBody
    public Object changeStatus(String id,String status){
        ResEntity result  = adminService.updateStatus(id,status);
        return result;
    }

    @RequestMapping(value="admin/edit/deleteLis")
    @LogAnnotaion(description = "删除管理员")
    @ResponseBody
    public Object deleteLis(String ids){
        ResEntity result  = null;
        try {
            result = adminService.deleteLis(ids);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResEntity(false,"服务异常,请稍后重试",null);
        }
        return result;
    }

}
