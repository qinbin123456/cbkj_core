package com.example.cbkj_core.controller;

import com.example.cbkj_core.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/menu")
public class SysMenuController {

    @Autowired
    private AdminMenuService adminMenuService;


}
