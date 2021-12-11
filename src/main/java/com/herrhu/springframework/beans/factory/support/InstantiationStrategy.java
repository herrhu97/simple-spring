package com.herrhu.springframework.beans.factory.support;

import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;
}
