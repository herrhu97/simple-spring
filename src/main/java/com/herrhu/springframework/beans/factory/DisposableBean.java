package com.herrhu.springframework.beans.factory;

/**
 * Interface to be implemented by beans that want to release resources
 * on destruction. A BeanFactory is supposed to invoke the destroy
 * method if it disposes a cached singleton. An application context
 * is supposed to dispose all of its singletons on close.
 */
public interface DisposableBean {

    /**
     * 类似于Cpp的析构函数概念，resource概念很广不一定是内存，或许是网络连接部分的关闭
     *
     * @throws Exception
     */
    void destroy() throws Exception;
}
