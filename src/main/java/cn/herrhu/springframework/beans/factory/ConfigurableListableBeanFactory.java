package cn.herrhu.springframework.beans.factory;

import cn.herrhu.springframework.beans.factory.config.AutowireCapableBeanFactory;
import cn.herrhu.springframework.beans.factory.config.BeanDefinition;

public interface ConfigurableListableBeanFactory extends  ListableBeanFactory, HierarchicalBeanFactory, AutowireCapableBeanFactory {
    public BeanDefinition getBeanDefinition(String beanName);
}
