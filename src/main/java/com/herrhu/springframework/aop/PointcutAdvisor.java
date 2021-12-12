package com.herrhu.springframework.aop;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/7 15:52
 */
public interface PointcutAdvisor extends Advisor {
    /**
     * Get the Pointcut that drives this advisor.
     */
    Pointcut getPointcut();
}
