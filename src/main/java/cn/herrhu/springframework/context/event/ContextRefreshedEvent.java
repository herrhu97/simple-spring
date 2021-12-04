package cn.herrhu.springframework.context.event;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/4 12:31
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
