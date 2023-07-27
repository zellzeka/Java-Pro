package ua.kiev.prog;

import java.lang.reflect.Proxy;

public class App {
    public static void main(String[] args) {
        EntityIntf entity = new Entity();

        EntityIntf proxy = (EntityIntf) Proxy.newProxyInstance(
                App.class.getClassLoader(),
                new Class[] { EntityIntf.class },
                new EntityHandler(entity)
        );

        System.out.println(entity.getName());
        System.out.println(entity.getAge());

        System.out.println("-------------------");

        System.out.println(proxy.getName());
        System.out.println(proxy.getAge());
    }
}
