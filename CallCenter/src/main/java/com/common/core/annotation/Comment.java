package com.common.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: Allen
 * Date: 11/29/12
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Comment {
    String value() default "";
}