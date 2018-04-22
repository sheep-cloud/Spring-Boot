package com.atguigu.springboot.controller;

import com.atguigu.springboot.exception.UserNotExisException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: colg
 */
@ControllerAdvice
public class MyExceptionHandler {

    // 1、浏览器客户端返回的都是json
//    @ResponseBody
//    @ExceptionHandler(UserNotExisException.class)
//    public Map<String, Object> handleException(Exception e) {
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", "user.notexist");
//        map.put("message", e.getMessage());
//        return map;
//    }

    @ExceptionHandler(UserNotExisException.class)
    public String handleException(Exception e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 传入我们自己的错误状态码 4xx 5xx
        request.setAttribute("javax.servlet.error.status_code", 500);
        map.put("code", "user.notexist");
        map.put("message", "用户出错啦");

        request.setAttribute("ext", map);
        // 转发到/error
        return "forward:/error";
    }
}
