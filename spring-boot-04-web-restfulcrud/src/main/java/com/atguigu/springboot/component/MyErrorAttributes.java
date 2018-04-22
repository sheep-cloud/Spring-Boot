package com.atguigu.springboot.component;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * 给容器中加入我们自己定义的ErrorAttributes
 *
 * @author: colg
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    /**
     * 返回的map就是页面和json能获取的所有字段
     *
     * @param requestAttributes
     * @param includeStackTrace
     *
     * @return
     */
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(requestAttributes, includeStackTrace);
        map.put("company", "atguigu");

        // 我们的异常处理器携带的数据
        Map<String, Object> ext = (Map<String, Object>) requestAttributes.getAttribute("ext", RequestAttributes.SCOPE_REQUEST);
        map.put("ext", ext);
        return map;
    }
}
