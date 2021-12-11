package com.herrhu.springframework.aop.framework;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/6 17:08
 */
public interface AopProxy {
    /**
     * 获取代理类
     *
     * @return
     */
    Object getProxy();
}
