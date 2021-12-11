package com.herrhu.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: HerrHu
 * @time: 2021/12/11 19:44
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";
}
