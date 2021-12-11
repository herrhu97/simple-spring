package com.herrhu.springframework.beans.factory;

/**
 * 实现此接口，即能感知到所属的BeanName
 */
public interface BeanNameAware {
    void setBeanName(String name);
}
