package com.herrhu.springframework.aop;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/6 16:22
 */
public interface Pointcut {
    /**
     * returns the ClassFilter for this PointCut
     *
     * @return
     */
    ClassFilter getClassFilter();

    /**
     * returns the MethodMatcher for this PointCut
     *
     * @return
     */
    MethodMatcher getMethodMatcher();
}
