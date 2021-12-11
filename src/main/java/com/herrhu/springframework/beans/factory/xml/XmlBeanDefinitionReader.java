package com.herrhu.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import com.herrhu.springframework.beans.BeansException;
import com.herrhu.springframework.beans.PropertyValue;
import com.herrhu.springframework.beans.factory.config.BeanDefinition;
import com.herrhu.springframework.beans.factory.config.BeanReference;
import com.herrhu.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.herrhu.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.herrhu.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import com.herrhu.springframework.core.io.Resource;
import com.herrhu.springframework.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 对xml文件的解析
 *
 * @author herrhu
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException | ClassNotFoundException | DocumentException exception) {
            throw new BeansException("IOException parsing XML document from " + resource, exception);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    /**
     * 非常核心的Spring xml配置解析方法，含有很多逻辑
     *
     * @param inputStream 输入的Resource
     * @throws ClassNotFoundException
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(inputStream);
        Element root = doc.getRootElement();

        //解析 context:component-scan标签，扫描包中的类并提取相关信息，用于组装BeanDefinition
        Element componentScan = root.element("component-scan");

        if (null != componentScan) {
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            //对注解的扫描逻辑在这里添加的，本质还是xml驱动，注解作为辅助使用
            scanPackage(scanPath);
        }

        List<Element> beanList = root.elements("bean");


        for (Element bean : beanList) {


            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethodName = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");

            //默认使用类的小驼峰名作为beanName
            Class<?> clazz = Class.forName(className);
            //优先级id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            //定义Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            beanDefinition.setInitMethodName(initMethodName);

            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            List<Element> propertyList = bean.elements("property");
            //读取属性并填充
            for (Element property : propertyList) {
                //解析标签：property
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");

                //获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                //创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            //注册Bean信息
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }


    }

    private void scanPackage(String scanPath) {
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}
