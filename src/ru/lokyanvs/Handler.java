package ru.lokyanvs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Handler implements InvocationHandler {

    private static Map<Map<Integer, Integer>, Integer> cacheSum = new HashMap<>();
    private static Map<Map<Integer, Integer>, Integer> cachePow = new HashMap<>();

    private final ProxyTestInterface original;

    public Handler(ProxyTestInterface original) {
        this.original = original;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<Integer, Integer> map = new HashMap<>();
        map.put((Integer) args[0], (Integer) args[1]);
        if (method.getName().equals("sum")) {
            if (method.isAnnotationPresent(Cache.class)) {
                if (cacheSum.containsKey(map)) {
                    System.out.print("Значение метода sum для аргументов " + args[0] + " " + args[1] + " взято из кеша: ");
                    return cacheSum.get(map);
                } else {
                    System.out.print("Производится расчет метода sum для аргументов " + args[0] + " " + args[1] + ": ");
                    int i = (Integer) method.invoke(original, args);
                    cacheSum.put(map, i);
                    return i;
                }
            }
        }
        if (method.getName().equals("pow")) {
            if (method.isAnnotationPresent(Cache.class)) {
                if (cachePow.containsKey(map)) {
                    System.out.print("Значение метода pow для аргументов " + args[0] + " " + args[1] + " взято из кеша: ");
                    return cachePow.get(map);
                } else {
                    System.out.print("Производится расчет метода pow для аргументов " + args[0] + " " + args[1] + ": ");
                    int i = (Integer) method.invoke(original, args);
                    cachePow.put(map, i);
                    return i;
                }
            }
        }

        System.out.print("Производится расчет метода " + method.getName() + " для аргументов " + args[0] + " " + args[1] + ": ");
        return method.invoke(original, args);
    }
}

