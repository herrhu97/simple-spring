package com.herrhu.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/11 19:45
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    String value();
}
