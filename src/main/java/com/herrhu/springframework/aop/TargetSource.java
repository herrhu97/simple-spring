package com.herrhu.springframework.aop;

/**
 * @description: 被代理的目标对象
 * @author: HerrHu
 * @time: 2021/12/6 16:29
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object object) {
        this.target = object;
    }

    public Class[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return this.target;
    }
}
