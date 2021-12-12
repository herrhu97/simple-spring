package com.herrhu.springframework.aop.framework.autoproxy;

import com.herrhu.springframework.aop.*;
import com.herrhu.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.herrhu.springframework.aop.framework.ProxyFactory;
import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.beans.PropertyValues;
import com.herrhu.springframework.beans.factory.BeanFactory;
import com.herrhu.springframework.beans.factory.BeanFactoryAware;
import com.herrhu.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.herrhu.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/7 16:08
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 实现的是接口BeanPostProcessor的方法，作用与bean的初始化之后
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors =
                beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            //过滤匹配类
            if (!classFilter.matches(bean.getClass())) {
                continue;
            }

            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = null;

            try {
                targetSource = new TargetSource(bean.getClass().getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }

            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            //返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }


}
