package com.herrhu.springframework.context.event;

import com.herrhu.springframework.context.ApplicationEvent;
import com.herrhu.springframework.context.ApplicationListener;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 12:32
 */
public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}
