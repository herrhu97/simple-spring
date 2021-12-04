package cn.herrhu.springframework.test;

import cn.herrhu.springframework.beans.PropertyValue;
import cn.herrhu.springframework.beans.PropertyValues;
import cn.herrhu.springframework.beans.factory.config.BeanDefinition;
import cn.herrhu.springframework.beans.factory.config.BeanReference;
import cn.herrhu.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.herrhu.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import cn.herrhu.springframework.context.support.ClassPathXmlApplicationContext;
import cn.herrhu.springframework.core.io.DefaultResourceLoader;
import cn.herrhu.springframework.core.io.Resource;
import cn.herrhu.springframework.test.bean.UserDao;
import cn.herrhu.springframework.test.bean.UserService;
import cn.herrhu.springframework.test.common.MyBeanFactoryPostProcessor;
import cn.herrhu.springframework.test.common.MyBeanPostProcessor;
import cn.herrhu.springframework.test.event.CustomEvent;
import cn.hutool.core.io.IoUtil;
import org.junit.Before;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import sun.awt.image.OffScreenImageSource;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.io.InputStream;


public class ApiTest {

    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("result is : " + result);
    }

    @Test
    public void test_xml() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);

        System.out.println(userService.queryUserInfo());

        System.out.println("ApplicationContextAware: " + userService.getApplicationContext());
        System.out.println("BeanFactory: " + userService.getBeanFactory());
    }

    @Test
    public void test_prototype() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService1 = applicationContext.getBean("userService", UserService.class);
        UserService userService2 = applicationContext.getBean("userService", UserService.class);

        System.out.println(userService1);
        System.out.println(userService2);

        System.out.println(userService1 + " hex hash code:" + Integer.toHexString(userService1.hashCode()));
        System.out.println(ClassLayout.parseInstance(userService1).toPrintable());
    }

    @Test
    public void test_factory_bean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService
                = applicationContext.getBean("userService", UserService.class);
        System.out.println(userService.queryUserInfo());
    }

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springEvent.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "success!"));
        applicationContext.registerShutdownHook();
    }
}
