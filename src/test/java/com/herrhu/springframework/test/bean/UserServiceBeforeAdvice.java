package com.herrhu.springframework.test.bean;

import com.herrhu.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author herrhu
 * @date 2021/12/12 12:02
 **/
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法：" + method.getName());
    }
}
