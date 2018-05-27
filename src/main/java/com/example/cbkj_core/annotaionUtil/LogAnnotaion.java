package com.example.cbkj_core.annotaionUtil;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotaion {
    String description()  default "";
}
