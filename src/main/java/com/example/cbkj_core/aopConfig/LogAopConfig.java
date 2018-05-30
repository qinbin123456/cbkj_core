package com.example.cbkj_core.aopConfig;

import com.alibaba.fastjson.JSONObject;
import com.example.cbkj_core.beans.LogEntity;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.annotaionUtil.LogAnnotaion;
import com.example.cbkj_core.annotaionUtil.TokenAnnotaion;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


@Aspect
@Component
public class LogAopConfig {

    @Pointcut("execution(public * com.example.cbkj_core.controller.*.*(..))")
    public void web(){}

    @AfterReturning(returning = "reo", pointcut = "web()")
    public void doAfterReturning(JoinPoint joinPoint,Object reo) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Method method = getMethod(joinPoint);
        if(null != method){

            String descpt = null;
            String methodName =  joinPoint.getSignature().getName();

            Object obj  = method.getAnnotation(TokenAnnotaion.class);
            Object logObj  = method.getAnnotation(LogAnnotaion.class);
            boolean showLog = false;
            boolean status = false;
            if(null != logObj){
                showLog = true;
                descpt = ((LogAnnotaion) logObj).description();
            }
            if(null != obj){
                if(((TokenAnnotaion)obj).submitP() && null != reo){
                    showLog = true;
                    ResEntity entity = (ResEntity) reo;
                    if(entity.getStatus()){
                        request.getSession(true).removeAttribute("token");
                    }
                }
            }
            if(null != reo){
                if(reo instanceof  ResEntity){
                    ResEntity entity = (ResEntity) reo;
                    status = entity.getStatus();
                }
            }
            if(showLog){
                LogEntity log = new LogEntity();
                log.setUrl(request.getRequestURL().toString());
                log.setHttpMethod(request.getMethod());
                log.setIp(request.getRemoteAddr());
                log.setClassName(joinPoint.getSignature().getDeclaringTypeName());
                log.setMethodName(methodName);
                log.setExecuType("afterReturning");
                log.setStatus(status?"success":"error");
                log.setDescr(descpt);
                System.out.println(JSONObject.toJSON(log).toString());
            }

        }

    }


    @AfterThrowing(pointcut = "web()", throwing = "e")
    private void doAfterThrow(JoinPoint joinPoint,  Throwable e) {
//         System.out.println(e.fillInStackTrace());
    }

    public Method getMethod(JoinPoint joinPoint){
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
