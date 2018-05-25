package com.example.cbkj_core.zAnnotaionUtil;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogAnnotaion {
    String description()  default "";
}
