package cn.herrhu.springframework.beans.factory.support;

import cn.herrhu.springframework.beans.BeansException;
import cn.herrhu.springframework.beans.factory.BeanFactory;
import cn.herrhu.springframework.beans.factory.FactoryBean;
import cn.herrhu.springframework.beans.factory.config.BeanDefinition;
import cn.herrhu.springframework.beans.factory.config.BeanPostProcessor;
import cn.herrhu.springframework.beans.factory.config.ConfigurableBeanFactory;
import cn.herrhu.springframework.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /**
     * ClassLoader to resolve bean name with, if necessary
     */
    private final ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requireType) throws BeansException {
        return (T)getBean(name);
    }

    public <T> T doGetBean(String name, Object... args) throws BeansException {
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            //如果是FactoryBean，则需要调用FactoryBean#getObject;不是直接返回sharedInstance
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }
}
