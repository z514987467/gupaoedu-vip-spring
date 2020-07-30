package com.gupaoedu.vip.spring.formework.aop;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-29 21:57
 **/
public interface GPAopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
