package com.herrhu.springframework.context.event;

import com.herrhu.springframework.context.ApplicationContext;
import com.herrhu.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 12:29
 */
public class ApplicationContextEvent extends ApplicationEvent {
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
