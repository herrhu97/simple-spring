package com.herrhu.springframework.beans.factory;

/**
 * 实现此接口感知到所属的ClassLoader
 */
public interface BeanClassLoaderAware extends Aware {
    void setBeanClassLoader(ClassLoader classLoader);
}
