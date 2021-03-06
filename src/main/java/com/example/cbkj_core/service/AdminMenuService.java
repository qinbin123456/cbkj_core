package com.example.cbkj_core.service;

import com.example.cbkj_core.beans.AdminMenu;
import com.example.cbkj_core.beans.Menu;
import com.example.cbkj_core.beans.ResEntity;
import com.example.cbkj_core.common.AdminUtils;
import com.example.cbkj_core.common.Page;
import com.example.cbkj_core.mapper.AdminMenuMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminMenuService {

    @Autowired
    private AdminMenuMapper adminMenuMapper;

    public List<AdminMenu> getAllMenu() {
        return adminMenuMapper.getAllMenu();
    }

    /**
     * 获取某个用户某个父类的菜单
     * @param uid
     * @param parent
     * @return
     */
    public Object getMenuByPID(String uid, int parent) {

        Map<String,Object> params = new HashMap<String,Object>();
        if(parent == 0){
            params.put("parent_mid",parent);
        }
        params.put("uid",uid);
        List<Menu> lisM = adminMenuMapper.getMenuByPID(params);
        List<Menu> resultLis = new ArrayList<Menu>();
        if(null != lisM){
            Map<Integer,List<Menu>> m = getChildren(lisM);
            for(Menu menu:lisM){
                if(parent == 0){
                    resultLis.add(menu);
                }else{
                    if(menu.getParent_mid() == parent){
                        List<Menu> lis = m.get(menu.getPid());
                        if(null != lis && lis.size()>0 ){
                            menu.setChildren(lis);
                        }
                        resultLis.add(menu);
                    }
                }

            }
        }
        return resultLis;
    }

    /**
     * 只取三级菜单
     * @param lis
     * @return
     */
    private Map<Integer,List<Menu>> getChildren(List<Menu> lis){
        Map<Integer,List<Menu>> resultM = new HashMap<Integer,List<Menu>>();
        for(Menu childrenM : lis){
            if(resultM.containsKey(childrenM.getParent_mid())){
                resultM.get(childrenM.getParent_mid()).add(childrenM);
            }else{
                List<Menu> lism = new ArrayList<Menu>();
                lism.add(childrenM);
                resultM.put(childrenM.getParent_mid(),lism);
            }
        }
        return resultM;
    }

    /**
     * 获取分页数据
     * @param adminMenu
     * @param page
     * @return
     */
    public Object getPageDatas(AdminMenu adminMenu, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit());
        List<Map<String,Object>> lis = adminMenuMapper.getPageDatas(adminMenu);
        Object result = Page.getLayUiTablePageData(lis);
        return result;
    }

    /**
     * 获取某个单条数据
     * @param id
     * @return
     */
    public ResEntity findObj(String id) {
        if(StringUtils.isBlank(id)){
            return new ResEntity(false,"参数错误（缺少参数）",null);
        }
        AdminMenu adminMenu = adminMenuMapper.selectByPrimaryKey(Integer.valueOf(id));
        if(null != adminMenu){
            return new ResEntity(true,"SUCCESS",adminMenu);
        }
        return new ResEntity(false,"数据不存在，检查参数",null);
    }

    /**
     * 插入
     * @param adminMenu
     * @return
     */
    public ResEntity insert(AdminMenu adminMenu) {
        adminMenu.setCteateId(AdminUtils.getCurrentHr().getId());
        long rows = adminMenuMapper.insert(adminMenu);
        if(rows >0){
            return new ResEntity(true,"SUCCESS",rows);
        }
        return new ResEntity(false,"新增失败，系统错误！",null);
    }

    /**
     * 修改
     * @param adminMenu
     * @return
     */
    public ResEntity update(AdminMenu adminMenu) {
        if(null == adminMenu.getMid()){
            return new ResEntity(false,"缺少参数，请稍后在提交",null);
        }
        long rows = adminMenuMapper.updateByPrimaryKey(adminMenu);
        if(rows > 0){
            return new ResEntity(true,"SUCCESS",rows);
        }
        return new ResEntity(false,"服务异常，请稍后重试",null);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    public ResEntity deleteLis(String ids) throws Exception{
        if(StringUtils.isBlank(ids)){
            return new ResEntity(false,"缺少参数",null);
        }
        long rowsR = adminMenuMapper.deleteRMbyMid(ids.split(","));
        long rowsA = adminMenuMapper.deleteBylis(ids.split(","));
        return new ResEntity(true,"SUCCESS",rowsA);
    }

    /**
     * 修改是否启用
     * @param id
     * @param enabled
     * @return
     */
    public ResEntity updateEnableds(String id, Integer enabled) {
        if(StringUtils.isBlank(id) || null == enabled){
            return new ResEntity(false,"缺少参数,请稍后重试",null);
        }
        Map<String,Object> params = new HashMap<>();
        params.put("mid",id);
        params.put("enabled",enabled);
        long rows = adminMenuMapper.updateEnabled(params);
        return new ResEntity(true,"SUCCESS",rows);
    }

    /**
     * 获取所有启用的菜单
     * @return
     */
    public Object getAllMenuListM() {
        return adminMenuMapper.selectAllMenuByM();
    }

    public List<Map<String,Object>> getBtnMenuLisByPath(String path,String rid) {
        Map<String,Object> params = new HashMap<>();
        params.put("path",path.substring(1,path.length()));
        params.put("rid",rid);
        return adminMenuMapper.getBtnMenuLisByPath(params);
    }
}
