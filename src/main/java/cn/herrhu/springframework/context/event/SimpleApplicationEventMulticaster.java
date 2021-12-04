package cn.herrhu.springframework.context.event;

import cn.herrhu.springframework.beans.factory.BeanFactory;
import cn.herrhu.springframework.context.ApplicationEvent;
import cn.herrhu.springframework.context.ApplicationListener;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 16:03
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener applicationListener : getApplicationListeners(event)) {
            applicationListener.onApplicationEvent(event);
        }
    }
}
