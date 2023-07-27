package ua.kiev.prog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityHandler implements InvocationHandler {
    private final EntityIntf original;

    public EntityHandler(EntityIntf original) {
        this.original = original;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Proxy works");

        if (method.getName() == "getAge")
            return 50;
        else
            return method.invoke(original, args);
    }
}
