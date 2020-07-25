package com.gupaoedu.vip.spring.formework.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-25 09:17
 **/
public class GPHandlerMapping {

    /**
     * 保存方法对应的实例
     */
    private Object controller;

    /**
     * 保存映射 的方法
     */
    private Method method;

    /**
     * URL的正则匹配
     */
    private Pattern pattern;

    public GPHandlerMapping(Pattern pattern,Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
