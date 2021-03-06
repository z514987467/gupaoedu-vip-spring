package com.gupaoedu.vip.spring.formework.aop.aspect;

import com.gupaoedu.vip.spring.formework.aop.intercept.GPMethodInterceptor;
import com.gupaoedu.vip.spring.formework.aop.intercept.GPMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-31 23:54
 **/
public class GPMethodBeforeAdviceInterceptor extends GPAbstractAspectAdvice implements GPAdvice, GPMethodInterceptor {

    private GPJoinPoint joinPoint;

    public GPMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method, Object[] args, Object target) throws Throwable {
        //method.invoke(target);
        super.invokeAdviceMethod(this.joinPoint,null,null);
    }

    @Override
    public Object invoke(GPMethodInvocation mi) throws Throwable {
        //从被织入的代码中才能拿到，JoinPoint
        this.joinPoint = mi;
        before(mi.getMethod(),mi.getArguments(),mi.getThis());
        return mi.proceed();
    }
}
