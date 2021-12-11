package com.herrhu.springframework.aop;

/**
 * @description: Class对象过滤
 * @author: HerrHu
 * @time: 2021/12/6 16:23
 */
public interface ClassFilter {
    /**
     * Should the pointcut apply to the given interface or target class?
     *
     * @param targetClass
     * @return
     */
    boolean matches(Class<?> targetClass);
}
