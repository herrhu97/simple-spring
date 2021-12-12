package com.herrhu.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/7 15:48
 */
public interface Advisor {
    /**
     * Return the advice part of this aspect. An advice may be an
     * interceptor, a before advice, a throws advice, etc.
     *
     * @return the advice that should apply if the pointcut matches
     * @see org.aopalliance.intercept.MethodInterceptor
     * @see BeforeAdvice
     */
    Advice getAdvisor();
}
