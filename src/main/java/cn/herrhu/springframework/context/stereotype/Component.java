package cn.herrhu.springframework.context.stereotype;

import java.lang.annotation.*;

/**
 * @author herrhu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
