package cn.herrhu.springframework.test.bean;

import cn.herrhu.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/7 16:21
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法：" + method.getName());
    }
}
