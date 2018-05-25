package com.example.cbkj_core.beans;

public class ResEntity {

    private String status;
    private String message;
    private Object Data;

    public ResEntity(){

    }

    public ResEntity(String status,Object data){
          this.status = status;
          this.Data = data;
    }

    public ResEntity(String status,String message,Object data){
        this.status = status;
        this.message = message;
        this.Data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
