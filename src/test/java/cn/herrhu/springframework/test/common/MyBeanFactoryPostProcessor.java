package cn.herrhu.springframework.test.common;

import cn.herrhu.springframework.beans.BeansException;
import cn.herrhu.springframework.beans.PropertyValue;
import cn.herrhu.springframework.beans.PropertyValues;
import cn.herrhu.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.herrhu.springframework.beans.factory.config.BeanDefinition;
import cn.herrhu.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");

        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }
}
