package com.example.cbkj_core.beans;

import java.util.Date;

/**
 * 角色
 */
public class AdminRule {
    private Integer rid;

    private String rname;

    private String rdescr;

    private Date createDate;

    private Integer adminId;

    private String rnameZh;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname == null ? null : rname.trim();
    }

    public String getRdescr() {
        return rdescr;
    }

    public void setRdescr(String rdescr) {
        this.rdescr = rdescr == null ? null : rdescr.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getRnameZh() {
        return rnameZh;
    }

    public void setRnameZh(String rnameZh) {
        this.rnameZh = rnameZh == null ? null : rnameZh.trim();
    }
}