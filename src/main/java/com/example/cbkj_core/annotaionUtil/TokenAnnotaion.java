package com.example.cbkj_core.annotaionUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenAnnotaion {

    boolean toP() default false;
    boolean submitP() default false;
    String description()  default "";
}
