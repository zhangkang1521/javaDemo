package org.zk.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeUtils {

    public static Unsafe getUnsafe() {
        try {
            // 获取 Unsafe 类中的 theUnsafe 字段
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            // 设置为可访问
            field.setAccessible(true);
            // 获取 Unsafe 实例
            return  (Unsafe) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
