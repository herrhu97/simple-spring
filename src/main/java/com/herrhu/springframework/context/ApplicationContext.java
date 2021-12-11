package com.herrhu.springframework.context;

import com.herrhu.springframework.beans.factory.HierarchicalBeanFactory;
import com.herrhu.springframework.beans.factory.ListableBeanFactory;
import com.herrhu.springframework.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
