package com.gupaoedu.vip.spring.formework.beans;

/**
 * @program: gupaoedu-vip-spring
 * @description: BeanWrapper是Spring底层经常使用的一个接口，简单来说是对Bean的一种包装，包括对Bean的属性、方法，数据等
 * @author: zhangmz
 * @create: 2020-07-21 23:38
 **/
public class GPBeanWrapper {

    private Object wrappedInstance;

    private Class<?> wrappedClass;

    public GPBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance() {
        return this.wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return this.wrappedInstance.getClass();
    }
}
