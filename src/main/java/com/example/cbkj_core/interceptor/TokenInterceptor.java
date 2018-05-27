package com.example.cbkj_core.interceptor;

import com.example.cbkj_core.common.IDUtil;
import com.example.cbkj_core.annotaionUtil.TokenAnnotaion;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 *
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            TokenAnnotaion annotation = method.getAnnotation(TokenAnnotaion.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.toP();
                if (needSaveSession) {
                    request.getSession(true).setAttribute("token",IDUtil.getID());
                }
                boolean needRemoveSession = annotation.submitP();

                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        StringBuffer sb = new StringBuffer();
                        sb.append("{\"status\":\"error\",\"msg\":\"");
                        sb.append("重复提交");
                        sb.append("\"}");
                        out.write(sb.toString());
                        out.flush();
                        out.close();
                        return false;
                    }
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(true).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}
