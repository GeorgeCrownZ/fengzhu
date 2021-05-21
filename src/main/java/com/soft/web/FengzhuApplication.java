package com.soft.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ServletComponentScan //打成war包需要
public class FengzhuApplication extends SpringBootServletInitializer {
    @Override  //打成war包需要继承SpringBootServletInitializer接口
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FengzhuApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(FengzhuApplication.class, args);
    }
}
