package com.gupaoedu.vip.spring.formework.aop.intercept;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-29 22:48
 **/
public class GPMethodInvocation {

    public GPMethodInvocation(
            Object proxy, Object target, Method method, Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {

        /*this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;*/
    }

    public Object proceed() throws Throwable{
        return null;
    }
}
