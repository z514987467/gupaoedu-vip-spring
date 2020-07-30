package com.gupaoedu.vip.spring.formework.aop.intercept;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-29 23:10
 **/
public interface GPMethodInterceptor {
    Object invoke(GPMethodInvocation invocation) throws Throwable;
}
