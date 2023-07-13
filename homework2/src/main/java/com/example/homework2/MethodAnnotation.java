package com.example.homework2;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodAnnotation{
    int a() default 2;
    int b() default 5;
}
