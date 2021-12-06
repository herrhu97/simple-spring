package cn.herrhu.springframework.aop;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/6 16:23
 */
public interface ClassFilter {
    /**
     * Should the pointcut apply to the given interface or target class?
     *
     * @param targetClass
     * @return
     */
    boolean matches(Class<?> targetClass);
}
