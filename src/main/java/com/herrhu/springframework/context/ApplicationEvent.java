package com.herrhu.springframework.context;

import java.util.EventObject;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 12:27
 */
public abstract class ApplicationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
