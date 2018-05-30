package com.example.cbkj_core.beans;

import java.util.Date;
import java.util.List;

/**
 * 权限
 */
public class AdminMenu {
    private Integer mid;

    private String mname;

    private String url;

    private String path;

    private String iconcls;

    private Integer enabled;

    private Integer parentMid;

    private Date createDate;

    private String cteateId;

    private List<AdminRule> rules;

    public List<AdminRule> getRules() {
        return rules;
    }

    public void setRules(List<AdminRule> rules) {
        this.rules = rules;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls == null ? null : iconcls.trim();
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getParentMid() {
        return parentMid;
    }

    public void setParentMid(Integer parentMid) {
        this.parentMid = parentMid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCteateId() {
        return cteateId;
    }

    public void setCteateId(String cteateId) {
        this.cteateId = cteateId;
    }
}