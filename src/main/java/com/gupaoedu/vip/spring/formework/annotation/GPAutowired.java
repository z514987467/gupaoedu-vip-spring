package com.gupaoedu.vip.spring.formework.annotation;

import java.lang.annotation.*;

/**
 * @program: gupaoedu-vip-spring
 * @description: 自动注入
 * @author: zhangmz
 * @create: 2020-07-22 23:14
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPAutowired {
    String value() default  "";
}
