package cn.herrhu.springframework.beans.factory;

import cn.herrhu.springframework.beans.BeansException;

import java.util.HashMap;

public interface BeanFactory {


    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;
}
