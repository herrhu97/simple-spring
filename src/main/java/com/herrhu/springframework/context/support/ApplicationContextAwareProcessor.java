package com.herrhu.springframework.context.support;

import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.beans.factory.config.BeanPostProcessor;
import com.herrhu.springframework.context.ApplicationContext;
import com.herrhu.springframework.context.ApplicationContextAware;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/3 20:09
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 由于ApplicationContext获取并不能直接在创建Bean时候就可以拿到，所以在refresh操作时，把
     * ApplicationContext写入到一个包装的BeanProcessor中去
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
