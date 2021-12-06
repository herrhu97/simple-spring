package cn.herrhu.springframework.aop.framework;

import cn.herrhu.springframework.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/6 17:09
 */
public class JDKDynamicAopProxy implements AopProxy, InvocationHandler {
    private final AdvisedSupport advised;

    public JDKDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advised = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                advised.getTargetSource().getTargetClass(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(),
                    method, args));
        }

        return method.invoke(advised.getTargetSource().getTarget(), args);
    }
}
