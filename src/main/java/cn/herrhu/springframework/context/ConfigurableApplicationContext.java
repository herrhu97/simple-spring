package cn.herrhu.springframework.context;

import cn.herrhu.springframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新容器
     * @throws BeansException
     */
    void refresh() throws BeansException;

    void registerShutdownHook();

    void close();
}
