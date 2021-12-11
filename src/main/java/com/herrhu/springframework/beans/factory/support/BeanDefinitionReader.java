package com.herrhu.springframework.beans.factory.support;

import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.core.io.Resource;
import com.herrhu.springframework.core.io.ResourceLoader;

/**
 * Simple interface for bean definition readers
 * 前两个方法交给接口的抽象类实现，以免污染具体的接口实现方法
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;
}
