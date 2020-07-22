package com.gupaoedu.vip.spring.formework.beans.config;

import lombok.Data;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-21 07:24
 **/
@Data
public class GPBeanDefinition {
    private String beanClassName;
    private boolean lazyInit = false;
    private String factoryBeanName;
    private boolean isSingleton = true;
}
