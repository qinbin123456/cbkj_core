package com.example.cbkj_core.common;

import com.example.cbkj_core.beans.AdminInfo;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by sang on 2017/12/30.
 */
public class AdminUtils {
    public static AdminInfo getCurrentHr() {
        return (AdminInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
