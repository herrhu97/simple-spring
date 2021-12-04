package cn.herrhu.springframework.test.event;

import cn.herrhu.springframework.context.ApplicationListener;
import cn.herrhu.springframework.context.event.ContextClosedEvent;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 16:17
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("context closed event: " + this.getClass().getName());
    }
}
