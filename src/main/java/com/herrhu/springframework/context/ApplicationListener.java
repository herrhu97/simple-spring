package com.herrhu.springframework.context;

import java.util.EventListener;

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * handle a application event
     *
     * @param event
     */
    void onApplicationEvent(E event);
}
