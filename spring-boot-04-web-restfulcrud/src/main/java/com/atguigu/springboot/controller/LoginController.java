package com.atguigu.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: colg
 */
@Controller
public class LoginController {

    public static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map,
                        HttpSession session) {
        // 默认登录密码
        String pwd = "123456";
        if (!StringUtils.isEmpty(username) && pwd.equals(password)) {
            log.info("username: {}", username);
            log.info("password: {}", password);
            // 登录成功，防止表单重复提交，可以重定向到主页
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        }
        // 登录失败
        map.put("msg", "用户名密码错误");
        return "login";
    }
}
