package com.example.cbkj_core.aopConfig;

import com.alibaba.fastjson.JSONObject;
import com.example.cbkj_core.annotaionUtil.BtnAnnotaion;
import com.example.cbkj_core.beans.LogEntity;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.annotaionUtil.LogAnnotaion;
import com.example.cbkj_core.annotaionUtil.TokenAnnotaion;
import com.example.cbkj_core.common.AdminUtils;
import com.example.cbkj_core.service.AdminMenuService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


@Aspect
@Component
public class AnnotaionConfig {

    @Autowired
    private AdminMenuService adminMenuService;


    @Pointcut("execution(public * com.example.cbkj_core.controller.*.*(..))")
    public void web(){}

    @AfterReturning(returning = "reo", pointcut = "web()")
    public void doAfterReturning(JoinPoint joinPoint,Object reo) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Method method = getMethod(joinPoint);
        if(null != method){
            Object tokenObj  = method.getAnnotation(TokenAnnotaion.class);
            Object btnObj  = method.getAnnotation(BtnAnnotaion.class);
            if(null != btnObj){
                if(((BtnAnnotaion)btnObj).btn()){
                    String uri = request.getRequestURI();
                    String path = uri.replace(request.getContextPath(), "");
                    if(!StringUtils.isBlank(path)){
                        String rid = AdminUtils.getCurrentHr().getRoles().get(0).getRid();
                        List<Map<String,Object>> lisM = adminMenuService.getBtnMenuLisByPath(path,rid );
                        request.setAttribute("btnLis",lisM);
                    }
                }
            }

            if(null != tokenObj){
                TokenAnnotaion tokenAnnotaion = ((TokenAnnotaion)tokenObj);
                if(tokenAnnotaion.submitP() && null != reo){
                    ResEntity entity = (ResEntity) reo;
                    if(!entity.getStatus()){
                        String token = request.getSession(true).getAttribute("tempToken").toString();
                        request.getSession(true).setAttribute("token",token);
                    }
                    request.getSession(true).removeAttribute("tempToken");
                }
            }

        }
    }

    @AfterThrowing(pointcut = "web()", throwing = "e")
    private void doAfterThrow(JoinPoint joinPoint,  Throwable e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Method method = this.getMethod(joinPoint);
        if(null != method) {
            String descpt = null;
            Object logObj  = method.getAnnotation(LogAnnotaion.class);
            Object obj  = method.getAnnotation(TokenAnnotaion.class);
            if(null != logObj){
                descpt = ((LogAnnotaion) logObj).description();
            }
            if(null != obj){
                TokenAnnotaion tokenAnnotaion = ((TokenAnnotaion)obj);
                if(tokenAnnotaion.submitP()||tokenAnnotaion.toP()){
                    String desc = tokenAnnotaion.description();
                    descpt = StringUtils.isBlank(desc)?descpt:desc;
                }
            }
            String methodName =  joinPoint.getSignature().getName();
            LogEntity log = new LogEntity();
            log.setUrl(request.getRequestURL().toString());
            log.setHttpMethod(request.getMethod());
            log.setIp(request.getRemoteAddr());
            log.setClassName(joinPoint.getSignature().getDeclaringTypeName());
            log.setMethodName(methodName);
            log.setExecuType("Exception");
            log.setStatus("error");
            log.setDescr(descpt);
            log.setErrMsg(e.toString());

            System.out.println(JSONObject.toJSON(log).toString());
        }
    }

    private Method getMethod(JoinPoint joinPoint){
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        String methodName =  joinPoint.getSignature().getName();
        Object[] objs = joinPoint.getArgs();
        Method resM = null;
        for (Method method : methods) {
            if(method.getName().equals(methodName)){
                if(method.getParameterTypes().length == objs.length){
                    resM = method;
                    break;
                }
            }
        }
        return resM;
    }

}
