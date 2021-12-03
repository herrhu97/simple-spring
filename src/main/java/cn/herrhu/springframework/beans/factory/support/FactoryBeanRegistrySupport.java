package cn.herrhu.springframework.beans.factory.support;

import cn.herrhu.springframework.beans.BeansException;
import cn.herrhu.springframework.beans.factory.BeanFactory;
import cn.herrhu.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:处理关于FactoryBean此类对象的注册操作
 * @author: HerrHu
 * @time: 2021/12/3 21:04
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    /**
     * Cache of singleton objects created by FactoryBeans: FactoryBean name -> object
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String name) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FacotryBean threw exception on object[" + name + "] creation", e);
        }
    }
}
