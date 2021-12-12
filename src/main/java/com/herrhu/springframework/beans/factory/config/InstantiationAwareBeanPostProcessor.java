package com.herrhu.springframework.beans.factory.config;

import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.beans.PropertyValues;

/**
 * 并没有重写BeanPostProcessor的方法，两个接口五个方法是独立的
 *
 * @author: HerrHu
 * @time: 2021/12/7 16:10
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * Apply this BeanPostProcessor <i>before the target bean gets instantiated</i>.
     * The returned bean object may be a proxy to use instead of the target bean,
     * effectively suppressing default instantiation of the target bean.
     * <p>
     * 在 Bean 对象执行初始化方法之前，执行此方法
     * 使用生成的代理对象
     *
     * @param beanClass bean的class
     * @param beanName  bean名称
     * @return 新的bean
     * @throws BeansException bean异常
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * Perform operations after the bean has been instantiated, via a constructor or factory method,
     * but before Spring property population (from explicit properties or autowiring) occurs.
     * <p>This is the ideal callback for performing field injection on the given bean instance.
     * See Spring's own {@link com.herrhu.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor}
     * for a typical example.
     * <p>
     * 在 Bean 对象执行初始化方法之后，执行此方法
     * 判断是否继续在createBean中继续操作，还是直接返回
     *
     * @param bean     bean实例
     * @param beanName bean名称
     * @return 是否继续在createBean中继续操作，还是直接返回
     * @throws BeansException bean异常
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * Post-process the given property values before the factory applies them
     * to the given bean. Allows for checking whether all dependencies have been
     * satisfied, for example based on a "Required" annotation on bean property setters.
     * <p>
     * 在 Bean 对象实例化完成后，设置属性操作之前执行此方法
     *
     * @param pvs      属性值
     * @param bean     bean实例
     * @param beanName bean名称
     * @return 修改后的属性值
     * @throws BeansException bean异常
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;
}
