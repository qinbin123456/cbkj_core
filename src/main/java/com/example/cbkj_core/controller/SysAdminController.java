package com.example.cbkj_core.controller;

import com.example.cbkj_core.beans.AdminInfo;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sys/admin")
public class SysAdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 前往管理者首页
     * @return
     */
    @RequestMapping("/index")
    public String toIndex(){
       return "admin/index";
    }

    /**
     * 分页数据
     * @param admin
     * @param page
     * @return
     */
    @RequestMapping(value = "/getPages")
    @ResponseBody
    public Object getApps(AdminInfo admin,Page page) {
        Object obj = adminService.getPageDatas(admin,page);
        return obj;
    }


}
