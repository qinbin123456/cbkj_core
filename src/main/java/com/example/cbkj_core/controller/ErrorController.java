package com.example.cbkj_core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/400")
    public String toError(){
         return "error/400";
    }

    @RequestMapping("/403")
    public String toError2(){
        return "error/403";
    }
}
