package com.gupaoedu.vip.spring.formework.webmvc.servlet;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-25 15:41
 **/
public class GPHandlerAdapter {
    public boolean supports(Object handler) {
        return (handler instanceof GPHandlerMapping);
    }

    public GPModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return null;
    }
}
