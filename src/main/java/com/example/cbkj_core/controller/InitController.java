package com.example.cbkj_core.controller;

import com.example.cbkj_core.beans.AdminInfo;
import com.example.cbkj_core.common.AdminUtils;
import com.example.cbkj_core.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class InitController {

    @Autowired
    private AdminMenuService adminMenuService;

    @Value("${system.name}")
    private String SYSNAME;

    @RequestMapping("loginP")
    public String loginP(){
        return "login";
    }

    @RequestMapping("toMain")
    public String toMain(Model model){
        AdminInfo adminInfo = AdminUtils.getCurrentHr();
        model.addAttribute("sysName",SYSNAME);
        model.addAttribute("loginName",adminInfo.getName());
        model.addAttribute("indexUrl",adminInfo.getRoles().get(0).getIndexUrl());
        return "main";
    }
    /**
     * 登录成功加载菜单
     * @param parent_id
     * @return
     */
    @RequestMapping("public/getTopMenu")
    @ResponseBody
    public Object getMenuTop(Integer parent_id){
        return adminMenuService.getMenuByPID(AdminUtils.getCurrentHr().getId(),parent_id);
    }

}
