package com.shch.lz.ssm.listener;

import com.shch.lz.ssm.annotation.BaseService;
import com.shch.lz.ssm.base.BaseInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * Created by Link at 21:35 on 3/29/18.
 */
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (null == contextRefreshedEvent.getApplicationContext().getParent()) {
            LOGGER.debug(">>>>>> spring init complete <<<<<<");
            Map<String, Object> baseServices = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(BaseService.class);
            for (Object service : baseServices.values()) {
                LOGGER.debug(">>>>>> {}.initMapper()<<<<<<", service.getClass().getName());
                try {
                    Method initMapper = service.getClass().getMethod("initMapper");
                    initMapper.invoke(service);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    LOGGER.error("init BaseService.initMapper() exception.", e);
                    e.printStackTrace();
                }
            }
            Map<String, BaseInterface> baseInterfaceBeans = contextRefreshedEvent.getApplicationContext().getBeansOfType(BaseInterface.class);
            for (Object bean : baseInterfaceBeans.values()) {
                LOGGER.debug(">>>>>> {}.init()<<<<<<", bean.getClass().getName());
                try {
                    Method init = bean.getClass().getMethod("init");
                    init.invoke(bean);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    LOGGER.error("init BaseInterface.init() exception.", e);
                    e.printStackTrace();
                }
            }
        }
    }
}
