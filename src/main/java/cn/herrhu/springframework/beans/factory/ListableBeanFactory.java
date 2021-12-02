package cn.herrhu.springframework.beans.factory;

import java.util.Map;

public interface ListableBeanFactory {
    public <T> Map<String, T> getBeansOfType(Class<T> type);

    public String[] getBeanDefinitionNames();
}
