package com.atguigu.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个类的所有方法的返回数据直接写给浏览器。（如果是对象转为json数据）
 *
 * @author: colg
 */
//@Controller
//@ResponseBody
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello world quick!";
    }

    // REST API的方式
}
