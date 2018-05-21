package com.example.cbkj_core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class InitController {

    @RequestMapping("test")
    @ResponseBody
    public Object test(){
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("aa","aa11");
        m.put("bb","bb11");
        return m;
    }

}
