package com.example.cbkj_core.beans;

public class ResEntity {

    private boolean status = true;
    private String message;
    private Object Data;

    public ResEntity(){

    }

    public ResEntity(boolean status,Object data){
          this.status = status;
          this.Data = data;
    }

    public ResEntity(boolean status,String message,Object data){
        this.status = status;
        this.message = message;
        this.Data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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
