package com.example.cbkj_core.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class InitController {

    @Value("${system.name}")
    private String SYSNAME;

    @RequestMapping("loginP")
    public String loginP(){
        return "login";
    }

    @RequestMapping("toMain")
    public String toMain(Model model){
        model.addAttribute("sysName",SYSNAME);
        return "main";
    }

}
