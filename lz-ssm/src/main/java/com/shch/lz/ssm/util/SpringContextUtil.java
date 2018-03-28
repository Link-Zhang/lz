package com.shch.lz.ssm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Link at 19:14 on 3/28/18.
 */
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context = null;

    private SpringContextUtil() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static boolean containsBean(String beanName) {
        return context.containsBean(beanName);
    }

    public static boolean isSingleton(String beanName) {
        return context.isSingleton(beanName);
    }

    public static Class getType(String beanName) {
        return context.getType(beanName);
    }

}
