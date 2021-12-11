package com.herrhu.springframework.beans.factory;

import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.herrhu.springframework.beans.factory.config.BeanDefinition;
import com.herrhu.springframework.beans.factory.config.BeanPostProcessor;
import com.herrhu.springframework.beans.factory.config.ConfigurableBeanFactory;

// ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    public BeanDefinition getBeanDefinition(String beanName);

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void preInstantiateSingletons() throws BeansException;


}
