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

    Handler(ProxyTestInterface original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        Map<Integer, Integer> map = new HashMap<>();
        if (method.getName().equals("sum")) {
            map.put((Integer) args[0], (Integer) args[1]);
            if (method.isAnnotationPresent(Cache.class) || original.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Cache.class)) {
                if (cacheSum.containsKey(map)) {
                    System.out.print("Значение метода sum для аргументов " + args[0] + " " + args[1] + " взято из кеша: ");
                    return cacheSum.get(map);
                } else {
                    System.out.print("Производится расчет стандартным методом sum для аргументов " + args[0] + " " + args[1] + ": ");
                    int i = (Integer) method.invoke(original, args);
                    cacheSum.put(map, i);
                    return i;
                }
            }
        }
        if (method.getName().equals("pow")) {
            map.put((Integer) args[0], (Integer) args[1]);
            if (method.isAnnotationPresent(Cache.class) || original.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Cache.class)) {
                if (cachePow.containsKey(map)) {
                    System.out.print("Значение метода pow для аргументов " + args[0] + " " + args[1] + " взято из кеша: ");
                    return cachePow.get(map);
                } else {
                    System.out.print("Производится расчет стандартным методом pow для аргументов " + args[0] + " " + args[1] + ": ");
                    int i = (Integer) method.invoke(original, args);
                    cachePow.put(map, i);
                    return i;
                }
            }
        }

        System.out.print("Производится расчет методом " + method.getName() + ": ");
        return method.invoke(original, args);
    }
}

