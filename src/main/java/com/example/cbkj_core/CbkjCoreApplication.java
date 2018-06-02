package com.example.cbkj_core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.cbkj_core.mapper")
public class CbkjCoreApplication {


    public static void main(String[] args) {
        SpringApplication.run(CbkjCoreApplication.class, args);
    }
}
