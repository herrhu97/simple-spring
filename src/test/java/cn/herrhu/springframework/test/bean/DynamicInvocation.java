package cn.herrhu.springframework.test.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description: JDK动态代理例子，用proxied成员存放被代理对象，使用method.invoke(proxied, args)来进行实际的方法调用
 * @author: HerrHu
 * @time: 2021/12/7 11:38
 */
public class DynamicInvocation implements InvocationHandler {

    private Object proxied;

    public DynamicInvocation(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass().getName());
        for (Class<?> anInterface : proxy.getClass().getInterfaces()) {
            System.out.println(anInterface.getName());
        }
        if (method.getName() == "queryUserInfo") {
            System.out.println("Proxy detected: " + method.getName() + " method is proxied");
            return method.invoke(proxied, args);
        }
        return null;
    }
}
