package com.gupaoedu.vip.spring.formework.aop.support;

import com.gupaoedu.vip.spring.formework.aop.aspect.GPAfterReturningAdviceInterceptor;
import com.gupaoedu.vip.spring.formework.aop.aspect.GPAfterThrowingAdviceInterceptor;
import com.gupaoedu.vip.spring.formework.aop.aspect.GPMethodBeforeAdviceInterceptor;
import com.gupaoedu.vip.spring.formework.aop.config.GPAopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: gupaoedu-vip-spring
 * @description:
 * @author: zhangmz
 * @create: 2020-07-29 22:16
 **/
public class GPAdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private GPAopConfig config;

    private Pattern pointCutClassPattern;

    /**
     * 每个方法对应的拦截器链
     */
    private transient Map<Method, List<Object>> methodCache;

    public GPAdvisedSupport(GPAopConfig config) {
        this.config = config;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public Object getTarget() {
        return this.target;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception {
        List<Object> cached = methodCache.get(method);
        //TODO 不是太明白
        if (cached == null) {
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cached = methodCache.get(m);
            //底层逻辑，对代理方法进行一个兼容处理
            this.methodCache.put(m, cached);
        }
        return cached;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    private void parse() {
        String pointCut = config.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");

        //pointCut=public .* com.gupaoedu.vip.spring.demo.service..*Service..*(.*)
        //玩正则
        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));

        try {

            methodCache = new HashMap<>();
            Pattern pattern = Pattern.compile(pointCut);

            Class aspectClass = Class.forName(this.config.getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<>();

            for (Method m : aspectClass.getMethods()) {
                aspectMethods.put(m.getName(), m);
            }

            for (Method m : this.targetClass.getMethods()) {
                String methonString = m.toString();
                if (methonString.contains("throws")) {
                    methonString = methonString.substring(0, methonString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methonString);
                if (matcher.matches()) {
                    //执行器链
                    List<Object> advices = new LinkedList<>();
                    //把每一个方法包装成 MethodIterceptor
                    //before
                    if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))) {
                        //创建一个Advivce
                        advices.add(new GPMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()), aspectClass.newInstance()));
                    }
                    //after
                    if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))) {
                        //创建一个Advivce
                        advices.add(new GPAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()), aspectClass.newInstance()));
                    }
                    //afterthrpwimg
                    if (!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))) {
                        //创建一个Advivce
                        GPAfterThrowingAdviceInterceptor throwingAdviceInterceptor = new GPAfterThrowingAdviceInterceptor(aspectMethods.get(config.getAspectAfterThrow()), aspectClass.newInstance());
                        throwingAdviceInterceptor.setThrowName(config.getAspectAfterThrowingName());
                        advices.add(throwingAdviceInterceptor);
                    }
                    //拦截器链放入拦截方法
                    methodCache.put(m, advices);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
