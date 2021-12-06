package cn.herrhu.springframework.context.support;

import cn.herrhu.springframework.beans.BeansException;
import cn.herrhu.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.herrhu.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.herrhu.springframework.beans.factory.config.BeanPostProcessor;
import cn.herrhu.springframework.beans.factory.config.ConfigurableBeanFactory;
import cn.herrhu.springframework.beans.factory.support.SimpleInstantiationStrategy;
import cn.herrhu.springframework.context.ApplicationEvent;
import cn.herrhu.springframework.context.ApplicationEventPublisher;
import cn.herrhu.springframework.context.ApplicationListener;
import cn.herrhu.springframework.context.ConfigurableApplicationContext;
import cn.herrhu.springframework.context.event.ApplicationEventMulticaster;
import cn.herrhu.springframework.context.event.ContextClosedEvent;
import cn.herrhu.springframework.context.event.ContextRefreshedEvent;
import cn.herrhu.springframework.context.event.SimpleApplicationEventMulticaster;
import cn.herrhu.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME =
            "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;
    /**
     * Spring容器的核心逻辑
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException {
        //1.创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();

        //2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //3.添加ApplicationContextAwareProcessor，让继承自ApplicationContextAware的Bean的对象都能感知所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //4.在Bean实例化之前，执行BeanFactoryPostProcessor （invoke factory processors registered as beans in the context)
        invokeBeanFactoryPostProcessors(beanFactory);

        //5.BeanPostProcessor需要提前与其他Bean对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        //6.初始化事件发布者
        initApplicationEventMulticaster();

        //7.注册事件监听器，从beanDefinitionMap里找类为Listener的BeanDefinition并返回对象
        registerListeners();

        //8.提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        //9.发布容器刷新完成事件
        finishRefresh();
    }

    protected void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    protected void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener applicationListener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    protected  abstract  void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap =
                beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requireType) throws BeansException {
        return getBeanFactory().getBean(name, requireType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        //发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        //执行销毁单例bean的销毁方法
        getBeanFactory().destroySingletons();
    }
}
