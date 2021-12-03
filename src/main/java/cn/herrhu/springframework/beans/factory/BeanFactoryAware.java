package cn.herrhu.springframework.beans.factory;

import cn.herrhu.springframework.beans.BeansException;

/**
 * @description: 实现此接口，即能感知到所属的BeanFactory
 * Interface to be implemented by beans that wish to be aware of their owning {@link BeanFactory}
 * @author: HerrHu
 * @time: 2021/12/3 20:04
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
