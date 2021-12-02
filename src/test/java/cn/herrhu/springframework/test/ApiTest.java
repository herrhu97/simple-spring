package cn.herrhu.springframework.test;

import cn.herrhu.springframework.beans.PropertyValue;
import cn.herrhu.springframework.beans.PropertyValues;
import cn.herrhu.springframework.beans.factory.config.BeanDefinition;
import cn.herrhu.springframework.beans.factory.config.BeanReference;
import cn.herrhu.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.herrhu.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import cn.herrhu.springframework.core.io.DefaultResourceLoader;
import cn.herrhu.springframework.core.io.Resource;
import cn.herrhu.springframework.test.bean.UserDao;
import cn.herrhu.springframework.test.bean.UserService;
import cn.hutool.core.io.IoUtil;
import org.junit.Before;
import org.junit.Test;
import sun.awt.image.OffScreenImageSource;

import java.io.IOException;
import java.io.InputStream;


public class ApiTest {
    private DefaultResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_classpath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_file() throws  IOException {
        Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_url() throws IOException {
        Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_xml() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        UserService userService = beanFactory.getBean("userService", UserService.class);

        String result = userService.queryUserInfo();
        System.out.println("result is : " + result);
    }




}
