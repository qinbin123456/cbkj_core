package com.example.cbkj_core.controller;

import com.example.cbkj_core.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sys/sysAdmin")
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


}
