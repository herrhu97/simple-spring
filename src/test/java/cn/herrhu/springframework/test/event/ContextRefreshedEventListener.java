package cn.herrhu.springframework.test.event;

import cn.herrhu.springframework.context.ApplicationListener;
import cn.herrhu.springframework.context.event.ContextRefreshedEvent;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 16:43
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("context refreshed event: " + this.getClass().getName());
    }
}
