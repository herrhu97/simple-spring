package cn.herrhu.springframework.context;

import cn.herrhu.springframework.beans.factory.HierarchicalBeanFactory;
import cn.herrhu.springframework.beans.factory.ListableBeanFactory;
import cn.herrhu.springframework.core.io.ResourceLoader;

import java.util.Map;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
