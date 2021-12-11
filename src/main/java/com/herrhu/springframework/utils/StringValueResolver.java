package com.herrhu.springframework.utils;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/11 15:56
 */
public interface StringValueResolver {

    /**
     * 解析字符串
     *
     * @param strVal
     * @return
     */
    String resolveStringValue(String strVal);
}
