package com.herrhu.springframework.aop;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/7 15:50
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    void before(Method method, Object[] args, Object target) throws Throwable;
}
