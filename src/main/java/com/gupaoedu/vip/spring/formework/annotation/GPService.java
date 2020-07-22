package com.gupaoedu.vip.spring.formework.annotation;

import java.lang.annotation.*;

/**
 * @program: gupaoedu-vip-spring
 * @description: 业务逻辑,注入接口
 * @author: zhangmz
 * @create: 2020-07-22 23:17
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPService {
    String value() default "";
}
