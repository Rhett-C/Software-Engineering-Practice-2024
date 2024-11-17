package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal 工具类
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    // 提供ThreadLocal对象,
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    // 根据键获取值
    /**
     * 实际类型参数是根据方法的调用上下文来确定的。在调用 get() 方法时，需要提供实际的类型参数，
     * 例如 String value = MyClass.<String>get(); 或者 String value = MyClass.get();
     * （根据上下文推断出类型）。
     * 
     * @param <T>
     * @return Object 转 T 类型
     */
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    // 存储键值对
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    // 清除ThreadLocal 防止内存泄漏
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
