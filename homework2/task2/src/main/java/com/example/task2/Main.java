package com.example.task2;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        final Class<?> cls = TextContainer.class;
        SaveTo annotation = cls.getAnnotation(SaveTo.class);
        String path = annotation.filePath();
        Method[] methods = cls.getMethods();
        for (Method method:methods) {
            if (method.isAnnotationPresent(Saver.class)){
                File file = new File(path);
                TextContainer obj = new TextContainer();
                try {
                    method.invoke(obj, file, obj.getHello());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
