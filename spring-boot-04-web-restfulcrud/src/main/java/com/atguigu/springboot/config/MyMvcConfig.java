package com.atguigu.springboot.config;

import com.atguigu.springboot.component.LoginHandlerInterceptor;
import com.atguigu.springboot.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 使用WebMvConfigurerAdapter可以来扩展SpirngMVC的功能 <br/>
 * <p>
 * EnableWebMvc 全面接管SpringMVC，所有的SpringMVC的自动配置都失效了
 *
 * @author: colg
 */
//@EnableWebMvc
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
        // 浏览器发送 /atguigu请求来到success
        registry.addViewController("/atguigu").setViewName("success");
    }

    /**
     * 所有的WebMvcConfigurerAdapter组件都会一起起作用 <br/>
     * <p>
     * Bean 将组件注册到容器
     *
     * @return
     */
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            /**
             * 配置首页视图
             * @param registry
             */
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }

            /**
             * 注册拦截器
             *
             * @param registry
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                // 静态资源： *.css, *.js
                // Spirng Boot 已经做好了静态资源映射
                registry.addInterceptor(new LoginHandlerInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/", "/index.html", "/user/login");
            }

            @Override
            public void addFormatters(FormatterRegistry registry) {
                // 日期格式化
                registry.addFormatter(new DateFormatter("yyyy.MM.dd HH.mm.ss"));
                registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
            }
        };
        return adapter;
    }

    /**
     * 注入自定义区域解析器
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }


}
