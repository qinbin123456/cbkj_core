package com.example.cbkj_core.controller;

import com.example.cbkj_core.annotaionUtil.BtnAnnotaion;
import com.example.cbkj_core.annotaionUtil.LogAnnotaion;
import com.example.cbkj_core.beans.LogEntity;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.service.LogEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogentityController {

    @Autowired
    private LogEntityService logEntityService;

    @RequestMapping("sys/log")
    @BtnAnnotaion(btn = true)
    public String toIndex(){
        return "log/index";
    }

    @RequestMapping("sys/log/getPages")
    @ResponseBody
    public Object getApps(LogEntity logEntity, Page page) {
        Object obj = logEntityService.getPageDatas(logEntity,page);
        return obj;
    }

    @RequestMapping(value="log/deleteLis")
    @LogAnnotaion(description = "删除日志")
    @ResponseBody
    public Object deleteLis(String ids) {
        ResEntity result   = logEntityService.deleteLis(ids);
        return result;
    }

    @RequestMapping("log/detail/toPage")
    public String updateP(Model model, String ID){
        model.addAttribute("id",ID);
        return "log/detail";
    }

    @RequestMapping("log/detail/findObj")
    @LogAnnotaion(description = "查看日志详情")
    @ResponseBody
    public Object getAdminInfo(String id){
        ResEntity result = logEntityService.findObj(id);
        return result;
    }
}
