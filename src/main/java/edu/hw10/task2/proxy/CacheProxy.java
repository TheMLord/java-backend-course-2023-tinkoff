package edu.hw10.task2.proxy;

import edu.hw10.task2.models.LogMessage;
import edu.hw10.task2.models.MethodCache;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Caching proxy class.
 */
public class CacheProxy implements InvocationHandler {
    private static final Logger CACHE_PROXY_LOGGER = LogManager.getLogger(CacheProxy.class.getName());

    private static final String METHOD_IN_CACHE_MESSAGE =
        "The {} method with the {} parameters was stored in the cache. Returned the result {}";
    private static final String METHOD_NOT_YET_IN_CACHE_MESSAGE =
        "The {} method with {} parameters is missing from the cache. Called the method and returned the result {}."
            + " Saved to the cache";
    private static final String UNCACHED_METHOD_MESSAGE =
        "The {} method with {} parameters is not cached. Called the method and returned the result";
    private static final String ERROR_SAVE_LOGGING_MESSAGE = "error writing a message {} to a file {}";

    private final Map<MethodCache, Object> cache;
    private final Path pathToLogging;
    private final Object instance;

    private final List<LogMessage> debugLogMessageList = new ArrayList<>();

    /**
     * Class constructor.
     *
     * @param instance      the monitored object for the proxy.
     * @param pathToLogging the path to the file for recording logger messages.
     */
    private CacheProxy(Object instance, Path pathToLogging) {
        this.instance = instance;
        this.pathToLogging = pathToLogging;
        this.cache = new HashMap<>();
    }

    /**
     * Factory method of creating a caching proxy.
     *
     * @param target        the monitored object for the proxy.
     * @param pathToLogging the path to the file for recording logger messages.
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(T target, Path pathToLogging) {
        return (T) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            new CacheProxy(target, pathToLogging)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var cached = findCacheAnnotation(method);

        if (cached != null) {
            return invokeWithCaching(method, args, cached);
        }

        CACHE_PROXY_LOGGER.info(
            UNCACHED_METHOD_MESSAGE,
            method.getName(),
            args
        );
        return method.invoke(instance, args);
    }

    /**
     * Method that returns the result of an intercepted method marked with a cache annotation.
     * <p>
     * The method checks whether there is a call to the intercepted method in the cache,
     * if so, it does not call the intercepted method from the monitored object,
     * but returns the result of such a method from the cache.
     * If the intercepted method is not in the cache, then it is called from the monitored object and returned.
     * And the method with the parameters and the result is saved in the cache.
     *
     * @param method intercepted proxy method called.
     * @param args   arguments of the intercepted method.
     * @param cached cached annotation
     * @return result of an intercepted method.
     */
    private Object invokeWithCaching(Method method, Object[] args, Cache cached)
        throws InvocationTargetException, IllegalAccessException {
        var calledMethod = new MethodCache(method, args);
        Object result;
        if (cache.containsKey(calledMethod)) {
            result = cache.get(calledMethod);
            informCacheEvent(METHOD_IN_CACHE_MESSAGE, true, method, args, result, cached.persist());

        } else {
            result = method.invoke(instance, args);
            cache.put(calledMethod, result);
            informCacheEvent(METHOD_NOT_YET_IN_CACHE_MESSAGE, false, method, args, result, cached.persist());
        }
        return result;
    }

    /**
     * Method checks if the intercepted method is marked with a Cache annotation.
     *
     * @return an instance of the annotation or null.
     */
    private Cache findCacheAnnotation(Method method) {
        return (Cache) Arrays.stream(method.getAnnotations()).filter(annotation -> annotation instanceof Cache)
            .findFirst()
            .orElse(null);
    }

    /**
     * Method of printing information from the logger.
     */
    private void informCacheEvent(
        String messageEvent,
        boolean takeCache,
        Method method,
        Object[] args,
        Object result,
        boolean isSave
    ) {
        if (isSave) {
            saveLogMessage(new LogMessage(LocalDateTime.now(), method, args, result, takeCache));
        }
        CACHE_PROXY_LOGGER.info(messageEvent, method.getName(), args, result);
    }

    /**
     * Saving a logger message to a file.
     * <p>
     * LoggerMessage format: time | nameMethod | argsMethod | result | take in cache
     */
    private void saveLogMessage(LogMessage logMessage) {
        debugLogMessageList.add(logMessage);
        try {
            Files.write(pathToLogging, logMessage.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            CACHE_PROXY_LOGGER.info(ERROR_SAVE_LOGGING_MESSAGE, logMessage, pathToLogging.getFileName());
            CACHE_PROXY_LOGGER.info(e.getMessage());
            CACHE_PROXY_LOGGER.info(e.getStackTrace());
        }
    }
}
