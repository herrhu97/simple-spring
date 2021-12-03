package cn.herrhu.springframework.test.bean;

import cn.herrhu.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/3 21:34
 */
public class ProxyBeanFactory implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) -> {
            // 添加排除方法
            if ("toString".equals(method.getName())) return this.toString();

                Map<String, String> map = new HashMap<>();
                map.put("10001", "herrhu1");
                map.put("10002", "herrhu2");
                map.put("10003", "herrhu3");

                return "you are proxied " + method.getName() + ":" + map.get(args[0].toString());
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
