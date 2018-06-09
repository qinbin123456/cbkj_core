package com.example.cbkj_core.beans;

import java.util.Date;

public class LogEntity {

    public String lid;
    public String url;
    public String httpMethod;
    public String ip;
    public String className;
    public String methodName;
    public String execuType;
    public String status;
    public String descr;
    public String errMsg;
    public Date create_date;
    public String create_id;

    public LogEntity(){

    }
    public LogEntity(String url,String httpMethod,String ip,String className,String methodName,String execuType,String status,String descr,String errMsg){
        this.url = url;
        this.httpMethod = httpMethod;
        this.ip = ip;
        this.className = className;
        this.methodName = methodName;
        this.execuType = execuType;
        this.status = status;
        this.descr = descr;
        this.errMsg = errMsg;
    }

    public String getCreate_id() {
        return create_id;
    }

    public void setCreate_id(String create_id) {
        this.create_id = create_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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
