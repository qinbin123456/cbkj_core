package com.example.cbkj_core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication

@MapperScan("com.example.cbkj_core.mapper")
public class CbkjCoreApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CbkjCoreApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(CbkjCoreApplication.class, args);
    }
}
