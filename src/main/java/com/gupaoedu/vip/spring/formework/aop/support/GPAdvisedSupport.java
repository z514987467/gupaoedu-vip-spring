package com.gupaoedu.vip.spring.formework.aop.support;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-29 22:16
 **/
public class GPAdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    public Class<?> getTargetClass(){
        return this.targetClass;
    }

    public Object getTarget(){
        return null;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception{
        return null;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public boolean pointCutMatch() {
        return false;
    }
}
