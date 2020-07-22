package com.gupaoedu.vip.spring.formework.annotation;

import java.lang.annotation.*;

/**
 * @program: gupaoedu-vip-spring
 * @description: 请求参数映射
 * @author: zhangmz
 * @create: 2020-07-22 23:15
 **/
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestParam {
    String value() default "";

    boolean required() default true;
}
