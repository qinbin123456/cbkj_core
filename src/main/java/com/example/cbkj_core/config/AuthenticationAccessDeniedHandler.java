package com.example.cbkj_core.config;

import com.alibaba.druid.util.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 20180521
 */
@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException e) {
        try{
            if(!StringUtils.isEmpty(req.getHeader("x-requested-with")) && req.getHeader("x-requested-with").equals("XMLHttpRequest")){
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.setContentType("application/json;charset=UTF-8");
                PrintWriter out = res.getWriter();
                out.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员!\"}");
                out.flush();
                out.close();
            }else{
                res.sendRedirect(req.getContextPath()+"/403");
            }
        }catch(Exception exc){
            System.out.println(exc.getMessage());
        }

    }
}
