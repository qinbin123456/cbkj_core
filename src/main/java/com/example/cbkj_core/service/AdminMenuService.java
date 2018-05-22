package com.example.cbkj_core.service;

import com.example.cbkj_core.beans.AdminMenu;
import com.example.cbkj_core.beans.Menu;
import com.example.cbkj_core.mapper.AdminMenuMapper;
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
    public Object getMenuByPID(Integer uid, int parent) {

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
}
