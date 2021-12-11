package cn.herrhu.springframework.context.annotation;

import cn.herrhu.springframework.beans.factory.config.BeanDefinition;
import cn.herrhu.springframework.context.stereotype.Component;
import cn.hutool.core.util.ClassUtil;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @description: 做注解扫描
 * @author: HerrHu
 * @time: 2021/12/11 14:27
 */
public class ClassPathScanningCandidateComponentProvider {

    /**
     * 扫描包，拿到基本的BeanDefinition信息
     *
     * @param basePackage xml中获取的目录信息
     * @return Bean定义的集合
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
