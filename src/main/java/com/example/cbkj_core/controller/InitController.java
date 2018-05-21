package com.example.cbkj_core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class InitController {

    @RequestMapping("loginP")
    public String loginP(){
        return "login";
    }

    @RequestMapping("toMain")
    public String toMain(){
        return "main";
    }

}
