package edu.hw10.task2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy implements InvocationHandler {
    private static final Logger CACHE_PROXY = LogManager.getLogger(CacheProxy.class.getName());
    private final Map<MethodCache, Object> cache;
    private final Class<?> target;
    private final Object instance;

    private CacheProxy(Object instance, Class<?> target) {
        this.instance = instance;
        this.target = target;
        this.cache = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(T target, Class<?> instance) {
        return (T) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            new CacheProxy(target, instance)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var calledMethod = new MethodCache(method, args);
        System.out.println(Arrays.toString(method.getAnnotations()));
        Object result;
        if (cache.containsKey(calledMethod)) {
            CACHE_PROXY.info("взяли из кэша");
            result = cache.get(calledMethod);
        } else {
            CACHE_PROXY.info("вызвали метод");
            result = target.getMethod(method.getName(), method.getParameterTypes()).invoke(instance, args);
            cache.put(calledMethod, result);
        }
        return result;
    }
}
