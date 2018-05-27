package com.example.cbkj_core.beans;

public class LogEntity {
    public String url;
    public String httpMethod;
    public String ip;
    public String className;
    public String methodName;
    public String execuType;
    public String status;
    public String descr;

    public LogEntity(){

    }

    public LogEntity(String url,String httpMethod,String ip,String className,String methodName,String execuType,String status,String descr){
        this.url = url;
        this.httpMethod = httpMethod;
        this.ip = ip;
        this.className = className;
        this.methodName = methodName;
        this.execuType = execuType;
        this.status = status;
        this.descr = descr;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getExecuType() {
        return execuType;
    }

    public void setExecuType(String execuType) {
        this.execuType = execuType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
