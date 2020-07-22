package com.gupaoedu.vip.spring.formework.annotation;

import java.lang.annotation.*;

/**
 * @program: gupaoedu-vip-spring
 * @description: 请求url
 * @author: zhangmz
 * @create: 2020-07-22 23:15
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestMapping {
    String value() default "";
}
