package com.atguigu.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author: colg
 */
@RestController
public class HelloController {

    @Value("${person.last-name}")
    private String name;

    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello " + name;
    }
}
