package com.herrhu.springframework.context.event;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 12:30
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
