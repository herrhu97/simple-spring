package cn.herrhu.springframework.beans.factory;

import cn.herrhu.springframework.beans.BeansException;
import cn.herrhu.springframework.beans.factory.config.AutowireCapableBeanFactory;
import cn.herrhu.springframework.beans.factory.config.BeanDefinition;
import cn.herrhu.springframework.beans.factory.config.BeanPostProcessor;
import cn.herrhu.springframework.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends  ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    public BeanDefinition getBeanDefinition(String beanName);

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void preInstantiateSingletons() throws BeansException;


}
