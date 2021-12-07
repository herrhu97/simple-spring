package cn.herrhu.springframework.aop;

import java.lang.reflect.Method;

/**
 * @description: 方法与Class对象匹配
 * @author: HerrHu
 * @time: 2021/12/6 16:23
 */
public interface MethodMatcher {

    /**
     * Perform static checking whether the given method matches.
     *
     * @param method      目标方法
     * @param targetClass 目标class对象
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);
}
