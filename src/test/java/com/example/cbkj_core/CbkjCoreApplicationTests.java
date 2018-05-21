package com.example.cbkj_core;

import com.example.cbkj_core.common.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CbkjCoreApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.encode("123456"));
    }

}
