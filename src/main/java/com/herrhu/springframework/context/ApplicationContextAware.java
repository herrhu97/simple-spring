package com.herrhu.springframework.context;

import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.beans.factory.Aware;

/**
 * 实现此接口，即能感知到所属的ApplicationContext
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
