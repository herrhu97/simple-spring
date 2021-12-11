package com.herrhu.springframework.beans.factory.config;

import com.herrhu.springframework.beans.BeansException;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/7 16:10
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}