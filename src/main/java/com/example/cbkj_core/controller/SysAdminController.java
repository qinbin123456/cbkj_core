package com.example.cbkj_core.controller;

import com.example.cbkj_core.beans.AdminInfo;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.service.AdminService;
import com.example.cbkj_core.zAnnotaionUtil.TokenAnnotaion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
public class SysAdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 前往管理者首页
     * @return
     */
    @RequestMapping("sys/admin/index")
    public String toIndex(){
       return "admin/index";
    }

    /**
     * 分页数据
     * @param admin
     * @param page
     * @return
     */
    @RequestMapping("sys/admin/getPages")
    @ResponseBody
//    @SysLogAnnotaion(description = "获取分页数据")
    public Object getApps(AdminInfo admin,Page page) {
        Object obj = adminService.getPageDatas(admin,page);
        return obj;
    }

    /**
     *
     * @param model
     * @param ID
     * @return
     */
    @RequestMapping("admin/edit/toPage")
    @TokenAnnotaion(toP = true)
    public String addOrUpdateP(Model model, String ID){

        return "admin/addOrUpdateP";
    }

    @RequestMapping("admin/edit/insert")
    @TokenAnnotaion(submitP = true)
    @ResponseBody
    public Object insert(AdminInfo adminInfo){
        ResEntity result = adminService.insert(adminInfo);
        return result;
    }



}
