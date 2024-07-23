package com.bjca.rcbademo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.bjca.rcbademo.dao")
@SpringBootApplication
public class RcbAdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcbAdemoApplication.class, args);
    }

}
