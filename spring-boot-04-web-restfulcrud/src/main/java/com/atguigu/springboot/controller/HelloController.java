package com.atguigu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: colg
 */
@Controller
public class HelloController {

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World";
    }

    /**
     * 查出一些数据，在页面展示
     *
     * @return
     */
    @GetMapping("/success")
    public String success(Map<String, Object> map) {
        map.put("hello", "<h1>你好</h1>");
        map.put("users", Arrays.asList("Jack", "Rose", "Tom"));
        // classpath:/templates/success.html
        System.out.println("HelloController.success");
        return "success";
    }
}
