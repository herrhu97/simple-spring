package com.herrhu.springframework.context.event;

import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.beans.factory.BeanFactory;
import com.herrhu.springframework.beans.factory.BeanFactoryAware;
import com.herrhu.springframework.context.ApplicationEvent;
import com.herrhu.springframework.context.ApplicationListener;
import com.herrhu.springframework.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 12:56
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster,
        BeanFactoryAware {
    private final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    //拿到event对应的listener，再执行listener的onApplicationEvent方法
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        LinkedList<ApplicationListener> allListeners = new LinkedList<ApplicationListener>();

        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) allListeners.add(listener);
        }
        return allListeners;
    }

    /**
     * ApplicationListener实现的ApplicationListener<T>所在的泛型类型T，与传入的ApplicationEvent是子父类关系
     *
     * @param applicationListener
     * @param event
     * @return
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();


        // 如果是cglib生成的不同的实例化类型，需要判断后获取目标class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ?
                listenerClass.getSuperclass() : listenerClass;
        //获取class实现的interface
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        //获取接口所带的泛型Type对象
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        //判定此eventClassName对象所表示的类或接口与指定的event.getClass()参数所表示的类或接口是否相同，或是否是其超类或超接口
        //isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是Object。如果
        //A.isAssignableFrom(B)结果是true，证明B可以转换成为A，也就是A可以由B转换而来
        return eventClassName.isAssignableFrom(event.getClass());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
