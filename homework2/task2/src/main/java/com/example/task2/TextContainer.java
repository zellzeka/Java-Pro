package com.example.task2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
@SaveTo
public class TextContainer {
    private String hello = "Hello Reflection";

    public TextContainer() {
    }
    public TextContainer(String hello) {
        this.hello = hello;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
    @Saver
    public void textWrite(File file, String text){
        try (FileWriter fw = new FileWriter(file)){
            fw.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        return "TextContainer{" +
                "hello='" + hello + '\'' +
                '}';
    }
}
