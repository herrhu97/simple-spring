package cn.herrhu.springframework.test;

import cn.herrhu.springframework.aop.AdvisedSupport;
import cn.herrhu.springframework.aop.TargetSource;
import cn.herrhu.springframework.aop.aspectj.AspectJExpressionPointcut;
import cn.herrhu.springframework.aop.framework.Cglib2AopProxy;
import cn.herrhu.springframework.aop.framework.JdkDynamicAopProxy;
import cn.herrhu.springframework.context.support.ClassPathXmlApplicationContext;
import cn.herrhu.springframework.test.bean.DynamicInvocation;
import cn.herrhu.springframework.test.bean.IUserService;
import cn.herrhu.springframework.test.bean.UserService;
import cn.herrhu.springframework.test.bean.UserServiceInterceptor;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Proxy;


public class ApiTest {
    private AdvisedSupport advisedSupport;

    @Before
    public void init() {
        // 目标对象
        IUserService userService = new UserService();
        // 组装代理信息
        advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* cn.herrhu.springframework.test.bean.IUserService.*(..))"));
    }


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
    public void test_aop() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-aop.xml");

        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

    @Test
    public void test_dynamic() {
        IUserService userService = new UserService();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        //JDK动态代理肯定要传入被代理的对象
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        //用切点表达式做MethodMatcher
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut(
                "execution(* cn.herrhu.springframework.test.bean.IUserService.*(..))"
        ));

        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println(proxy_cglib.getClass().getName());
        System.out.println("测试结果：" + proxy_cglib.register("粽粽"));
    }
}
