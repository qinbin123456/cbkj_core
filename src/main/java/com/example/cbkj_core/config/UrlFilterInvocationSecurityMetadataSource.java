package com.example.cbkj_core.config;

import com.example.cbkj_core.beans.AdminMenu;
import com.example.cbkj_core.beans.AdminRule;
import com.example.cbkj_core.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    AdminMenuService adminMenuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        FilterInvocation invocation = ((FilterInvocation) o);
        HttpSession session = invocation.getRequest().getSession();

        String requestUrl = invocation.getRequestUrl();
        if ("/loginP".equals(requestUrl)) {
            session.removeAttribute(Constont.MENUS);
            return null;
        }

        List<AdminMenu> allMenu = null;
        if(null == session.getAttribute(Constont.MENUS)){
            allMenu = adminMenuService.getAllMenu();
            session.setAttribute(Constont.MENUS,allMenu);
        }else{
            allMenu = (List<AdminMenu>) session.getAttribute(Constont.MENUS);
        }
        for (AdminMenu menu : allMenu) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRules().size()>0) {
                List<AdminRule> roles = menu.getRules();
                int size = roles.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getRname();
                }
                return SecurityConfig.createList(values);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
