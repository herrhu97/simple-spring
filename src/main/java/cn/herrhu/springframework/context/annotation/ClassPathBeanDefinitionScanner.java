package cn.herrhu.springframework.context.annotation;

import cn.herrhu.springframework.beans.factory.config.BeanDefinition;
import cn.herrhu.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.herrhu.springframework.context.stereotype.Component;
import cn.hutool.core.util.StrUtil;

import java.util.Set;

/**
 * @description: 对扫描信息加工，并加入到registry中
 * @author: HerrHu
 * @time: 2021/12/11 14:31
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            //对已经拿到的只含有beanClass信息的BeanDefinition加工
            for (BeanDefinition beanDefinition : candidates) {
                //解析Bean的作用域singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
    }

    /**
     * 获取Scope注解的value值
     *
     * @param beanDefinition Bean的定义
     * @return value
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 如果Component注解有值，使用此值作为beanName；否则使用Class的第一个字母变小写作为名字
     *
     * @param beanDefinition Bean的定义
     * @return value
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
