package com.example.homework2;

public class TestAnnotation {
    @MethodAnnotation
    public void test(int a, int b){
        System.out.println("Переменные " + a + " и " + b + " получены через аннотацию");
    }
}
