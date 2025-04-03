package org.zk.jol;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

@Slf4j
public class JolTest {


    @Test
    public void obj() {
        JolDto dto = new JolDto();
        dto.setId(100);
        dto.setAge(19);
        // markWord(8字节) + classPoint(4字节 默认指针压缩，不压缩8字节)
        log.info("{}", ClassLayout.parseInstance(dto).toPrintable());
    }

    @Test
    public void array() {
        long[] array = new long[4];
        array[0] = 11;
        array[1] = 22;
        array[2] = 33;
        // mark word + class point + array length + 数据
        System.out.println( ClassLayout.parseInstance(array).toPrintable() );
    }
}
