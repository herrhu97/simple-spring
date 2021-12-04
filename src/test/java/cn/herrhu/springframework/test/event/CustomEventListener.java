package cn.herrhu.springframework.test.event;

import cn.herrhu.springframework.context.ApplicationListener;

import java.util.Date;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 16:47
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("get: " + event.getSource() + "msg; time：" + new Date());
        System.out.println("msg: " + event.getId() + ":" + event.getMessage());
    }
}
