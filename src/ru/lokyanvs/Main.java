package ru.lokyanvs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        TestInterfaceClass testClass = new TestInterfaceClass("some text", 1, true);
        Class myClass = testClass.getClass();

        System.out.println("Все методы класса TestClass, включая родительские");
        for (Method m : myClass.getDeclaredMethods()) System.out.println(m);
        while ((myClass = myClass.getSuperclass()) != null) {
            for (Method m : myClass.getDeclaredMethods()) System.out.println(m);
        }

        System.out.println();
        myClass = testClass.getClass();
        Method[] methods = myClass.getDeclaredMethods();
        System.out.println("Геттеры класса TestClass");
        for (Method method : methods)
            if (method.getName().contains("get")) System.out.println(method);

        System.out.println();
        Field[] fields = myClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                if (Modifier.isStatic(field.getModifiers()))
                    try {
                        if (field.getName().equals(field.get(testClass)))
                            System.out.println("Значение поля " + field.getName() + " равно имени");
                        else System.out.println("Значение поля " + field.getName() + " не равно имени");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
            }
        }

        Handler handler = new Handler(testClass);
        ProxyTestInterface proxyTestInterface = (ProxyTestInterface) Proxy.newProxyInstance(ProxyTestInterface.class.getClassLoader(), new Class[]{ProxyTestInterface.class}, handler);

        System.out.println();
        System.out.println(proxyTestInterface.sum(1, 2));
        System.out.println(proxyTestInterface.sum(1, 2));
        System.out.println(proxyTestInterface.sum(2, 1));
        System.out.println(proxyTestInterface.sum(2, 1));
        System.out.println(proxyTestInterface.sum(2, 2));
        System.out.println(proxyTestInterface.sum(2, 2));

        System.out.println();

        System.out.println(proxyTestInterface.pow(2, 2));
        System.out.println(proxyTestInterface.pow(2, 2));
        System.out.println(proxyTestInterface.pow(3, 2));
        System.out.println(proxyTestInterface.pow(3, 2));
        System.out.println(proxyTestInterface.pow(4, 2));
        System.out.println(proxyTestInterface.pow(4, 2));
    }
}
