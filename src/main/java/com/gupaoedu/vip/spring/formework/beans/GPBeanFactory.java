package com.gupaoedu.vip.spring.formework.beans;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-21 07:04
 **/
public interface GPBeanFactory {
    /**
     * 根据BeanName从IOC容器中获取一个实例Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;
}
