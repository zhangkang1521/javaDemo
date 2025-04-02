package org.zk.unsafe;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@Slf4j
public class UnsafeTest {

    @Test
    @SneakyThrows
    public void fieldOffset() {
        Unsafe unsafe = UnsafeUtils.getUnsafe();
        SimpleObject simpleObject = new SimpleObject();
        Field field = simpleObject.getClass().getDeclaredField("count");
        long offset = unsafe.objectFieldOffset(field);
        // 如果启用了指针压缩并且使用的是64位JVM，对象头通常是12字节（Mark Word 8字节 + Class Pointer 4字节）
        log.info("offset:{}", offset);

        // 修改字段值
        unsafe.putInt(simpleObject, offset, 88);

        // 验证修改
        log.info("Modified count: {}", unsafe.getInt(simpleObject, offset));
    }
}
