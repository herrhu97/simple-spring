package cn.herrhu.springframework.test;

import cn.herrhu.springframework.beans.factory.config.BeanDefinition;
import cn.herrhu.springframework.beans.factory.BeanFactory;
import cn.herrhu.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.herrhu.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_BeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

        UserService userServiceSingleton = (UserService) beanFactory.getSingleton("userService");
        userService.queryUserInfo();
    }
}
