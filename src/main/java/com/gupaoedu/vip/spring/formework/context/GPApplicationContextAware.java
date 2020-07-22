package com.gupaoedu.vip.spring.formework.context;

/**
 * @program: gupaoedu-vip-spring
 * @description: 通过解耦的方式获得IOC容器的顶层接口
 * @author: zhangmz
 * @create: 2020-07-21 07:33
 **/
public interface GPApplicationContextAware {
    void setApplicationContext(GPApplicationContext applicationContext);
}
