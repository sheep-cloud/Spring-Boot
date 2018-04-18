package com.atguigu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @SpringBootApplication 来标注一个主程序类，说明这是一个SpringBoot应用
 *
 * @author: colg
 */
@SpringBootApplication
public class SpringBoot01HelloworldApplication {

    public static void main(String[] args) {
        // Spring 应用启动
        SpringApplication.run(SpringBoot01HelloworldApplication.class, args);
    }
}
