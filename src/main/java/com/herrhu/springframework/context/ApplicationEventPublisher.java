package com.herrhu.springframework.context;

public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
