package cn.herrhu.springframework.beans.factory;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {
    public <T> Map<String, T> getBeansOfType(Class<T> type);

    public String[] getBeanDefinitionNames();
}
