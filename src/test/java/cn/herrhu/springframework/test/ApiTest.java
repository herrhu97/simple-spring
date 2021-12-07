package cn.herrhu.springframework.test;

import cn.herrhu.springframework.aop.AdvisedSupport;
import cn.herrhu.springframework.aop.TargetSource;
import cn.herrhu.springframework.aop.aspectj.AspectJExpressionPointCut;
import cn.herrhu.springframework.aop.framework.Cglib2AopProxy;
import cn.herrhu.springframework.aop.framework.JDKDynamicAopProxy;
import cn.herrhu.springframework.test.bean.DynamicInvocation;
import cn.herrhu.springframework.test.bean.IUserService;
import cn.herrhu.springframework.test.bean.UserService;
import cn.herrhu.springframework.test.bean.UserServiceInterceptor;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class ApiTest {


    @Test
    public void test_proxy_class() {
        IUserService userService = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserService.class}, (proxy, method, args) -> "你被代理了!");
        System.out.println(userService.queryUserInfo());
    }

    @Test
    public void test_proxy_method() {
        Object proxied = new UserService();
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                proxied.getClass().getInterfaces(), new DynamicInvocation(proxied));
        System.out.println(proxy.queryUserInfo());
    }

    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointCut pointCut = new AspectJExpressionPointCut(
                "execution(* cn.herrhu.springframework.test.bean.IUserService.*(..))"
        );
        Class<UserService> clazz = UserService.class;
        Method queryUserInfo = clazz.getDeclaredMethod("queryUserInfo");
        System.out.println(pointCut.matches(clazz));
        System.out.println(pointCut.matches(queryUserInfo, clazz));
    }

    @Test
    public void test_dynamic() {
        IUserService userService = new UserService();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        //JDK动态代理肯定要传入被代理的对象
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        //用切点表达式做MethodMatcher
        advisedSupport.setMethodMatcher(new AspectJExpressionPointCut(
                "execution(* cn.herrhu.springframework.test.bean.IUserService.*(..))"
        ));

        IUserService proxy_jdk = (IUserService) new JDKDynamicAopProxy(advisedSupport).getProxy();
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println(proxy_cglib.getClass().getName());
        System.out.println("测试结果：" + proxy_cglib.register("粽粽"));
    }
}
