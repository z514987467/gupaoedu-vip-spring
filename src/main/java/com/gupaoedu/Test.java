package com.gupaoedu;

import com.gupaoedu.vip.spring.formework.context.GPApplicationContext;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-21 06:55
 **/
public class Test {
    public static void main(String[] args) {
        GPApplicationContext context = new GPApplicationContext("application.properties");
        try {
            Object object = context.getBean("MyAction");
            System.out.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(context);
    }
}
