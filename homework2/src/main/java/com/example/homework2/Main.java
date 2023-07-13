package com.example.homework2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {

        final Class<?> cls = TestAnnotation.class;
        Method[] testMethods = cls.getMethods();

        for (Method method: testMethods) {
            if (method.isAnnotationPresent(MethodAnnotation.class)){
                MethodAnnotation annotation = method.getAnnotation(MethodAnnotation.class);
                int firstParam = annotation.a();
                int secondParam = annotation.b();
                try {
                    TestAnnotation test = new TestAnnotation();
                    method.invoke(test, firstParam, secondParam);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
