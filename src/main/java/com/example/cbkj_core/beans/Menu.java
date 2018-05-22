package com.example.cbkj_core.beans;

import java.util.List;

/**
 * 菜单
 */
public class Menu {
    private int pid;
    private String title;
    private String url;
    private String href;
    private String icon;
    private int parent_mid;
    private List<Menu> children;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getParent_mid() {
        return parent_mid;
    }

    public void setParent_mid(int parent_mid) {
        this.parent_mid = parent_mid;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
