package com.herrhu.springframework.beans.factory.support;

import com.herrhu.springframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String beanName);

    Boolean containsBeanDefinition(String beanName);

    String[] getBeanDefinitionNames();
}
