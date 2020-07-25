package com.gupaoedu.vip.spring.formework.context;

import com.gupaoedu.vip.spring.formework.annotation.GPAutowired;
import com.gupaoedu.vip.spring.formework.annotation.GPController;
import com.gupaoedu.vip.spring.formework.annotation.GPService;
import com.gupaoedu.vip.spring.formework.beans.GPBeanFactory;
import com.gupaoedu.vip.spring.formework.beans.GPBeanWrapper;
import com.gupaoedu.vip.spring.formework.beans.config.GPBeanDefinition;
import com.gupaoedu.vip.spring.formework.beans.config.GPBeanPostProcessor;
import com.gupaoedu.vip.spring.formework.beans.support.GPBeanDefinitionReader;
import com.gupaoedu.vip.spring.formework.beans.support.GPDefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: gupaoedu-vip-spring
 * @description: IOC容器的顶层设计
 * @author: zhangmz
 * @create: 2020-07-21 07:07
 **/
public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {

    private String[] configLocations;

    private GPBeanDefinitionReader reader;

    /**
     * 单例的IOC容器缓存
     */
    private Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 通用的IOC容器
     */
    private Map<String, GPBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    public GPApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        refreash();
    }

    @Override
    public void refreash() {
        //1.定位配置文件
        reader = new GPBeanDefinitionReader(this.configLocations);

        //2.加载配置文件，扫描相关的类，把他们封装成BeanDefinition
        List<GPBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3.注册，把配置信息放到容器里面（伪IOC容器）
        doRegisterBeanDefinition(beanDefinitions);

        //4.把不是延时加载的类提前初始化
        doAutowrited();
    }

    private void doAutowrited() {
        for (Map.Entry<String, GPBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private void doRegisterBeanDefinition(List<GPBeanDefinition> beanDefinitions) {
        for (GPBeanDefinition beanDefinition : beanDefinitions) {
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
        //到这里为止，容器初始化完毕
    }

    //依赖注入，从这里开始，通过读取BeanDefinition中的信息
    //然后，通过反射机制创建一个实例并返回
    //Spring做法是，不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
    //装饰器模式：
    //1、保留原来的OOP关系
    //2、我需要对它进行扩展，增强（为了以后AOP打基础）
    @Override
    public Object getBean(String beanName) throws Exception {
        //1.初始化
        GPBeanWrapper beanWrapper = instantiateBean(beanName, this.beanDefinitionMap.get(beanName));

        GPBeanPostProcessor beanPostProcessor = new GPBeanPostProcessor();

        beanPostProcessor.postProcessBeforeInitialization(beanWrapper, beanName);

        //2.拿到beanWrapper之后，把BeanWrapper保存到IOC容器中
        /*if (this.factoryBeanInstanceCache.containsKey(beanName)) {
            throw new Exception("The" + beanName + "is exists");
        }*/
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);

        //3.注入
        populateBean(beanName, new GPBeanDefinition(), beanWrapper);

        beanPostProcessor.postProcessAfterInitialization(beanWrapper, beanName);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean(beanClass.getName());
    }

    private GPBeanWrapper instantiateBean(String beanName, GPBeanDefinition gpBeanDefinition) {
        //1.拿到要实例化的对象的类名
        String className = gpBeanDefinition.getBeanClassName();

        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
            //gpBeanDefinition.getFactoryBeanName()
            //假设默认就是单例
            if (factoryBeanObjectCache.containsKey(className)) {
                instance = this.factoryBeanObjectCache.get(className);
            } else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.factoryBeanObjectCache.put(className, instance);
                this.factoryBeanObjectCache.put(gpBeanDefinition.getFactoryBeanName(), instance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //3、把这个对象封装到beanWrapper中
        GPBeanWrapper beanWrapper = new GPBeanWrapper(instance);

        //4、把beanWrapper存到IOC容器中
        return beanWrapper;
    }

    private void populateBean(String beanName, GPBeanDefinition gpBeanDefinition, GPBeanWrapper gpBeanWrapper) {
        Object instance = gpBeanWrapper.getWrappedInstance();
        Class<?> clazz = gpBeanWrapper.getWrappedClass();
        if (!(clazz.isAnnotationPresent(GPController.class) || clazz.isAnnotationPresent(GPService.class))) {
            return;
        }

        //获得所有的fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(GPAutowired.class)) {
                continue;
            }

            GPAutowired autowired = field.getAnnotation(GPAutowired.class);
            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {
                if (this.factoryBeanInstanceCache.get(autowiredBeanName) == null) {
                    continue;
                }
                field.set(instance, this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount(){
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }
}
