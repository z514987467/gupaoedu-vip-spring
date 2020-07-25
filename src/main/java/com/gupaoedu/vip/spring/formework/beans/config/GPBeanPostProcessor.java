package com.gupaoedu.vip.spring.formework.beans.config;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-23 23:00
 **/
public class GPBeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
