package com.herrhu.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.beans.PropertyValues;
import com.herrhu.springframework.beans.factory.BeanFactory;
import com.herrhu.springframework.beans.factory.BeanFactoryAware;
import com.herrhu.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.herrhu.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.herrhu.springframework.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/11 19:48
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 1. 处理注解@Value
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }


        // 2. 处理注解@Autowired
        for (Field field : declaredFields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (null != autowiredAnnotation) {
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if (null != qualifierAnnotation) {
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                } else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }


}
