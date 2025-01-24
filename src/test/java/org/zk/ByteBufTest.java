package org.zk;

import io.netty.buffer.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangkang
 * @date 2024/11/19 18:01
 */
@Slf4j
public class ByteBufTest {

    @Test
    public void testIndex() {
        // netty提供的工具类，方便非池化缓冲区创建
        ByteBuf byteBuf = Unpooled.buffer(5);
        // 默认是Pooled实现；可通过-Dio.netty.allocator.type=unpooled修改
        // ByteBuf byteBuf2 = ByteBufAllocator.DEFAULT.buffer(5, 10);
        printBuf(byteBuf);

        // 会自动扩容
        for (int i = 0; i < 6; i++) {
            byteBuf.writeByte('a' + i);
        }
        printBuf(byteBuf);

        // 不能超过writeIndex
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        log.info("readByte:{}", bytes);

        printBuf(byteBuf);
    }

    private void printBuf(ByteBuf buf) {
        log.info("readerIndex:{} writerIndex:{} capacity:{} maxCapacity:{}",
                buf.readerIndex(),
                buf.writerIndex(),
                buf.capacity(),
                buf.maxCapacity());
    }

    @Test
    public void testRelease() {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.buffer(5);
        log.info("初始:{}", byteBuf.refCnt());


        byteBuf.retain();
        // ReferenceCountUtil.retain(byteBuf); 作用和 retain()一样
        log.info("retain之后:{}", byteBuf.refCnt());
        byteBuf.release();
        log.info("release之后:{}", byteBuf.refCnt());

        byteBuf.release();
        log.info("再release之后:{}", byteBuf.refCnt());

        // 会报错，refCnt为0，不能再操作
        byteBuf.writeInt(1);
    }

    @Test
    public void testAllocByteBuf() {
        // 非池化分配器-堆内存
        ByteBuf unpooledHeapBuffer = UnpooledByteBufAllocator.DEFAULT.heapBuffer();
        printBuf2(unpooledHeapBuffer);
        // 非池化分配器-直接内存
        ByteBuf unpooledDirectBuffer = UnpooledByteBufAllocator.DEFAULT.directBuffer();
        printBuf2(unpooledDirectBuffer);

        // 池化分配器-堆内存
        ByteBuf pooledHeapBuffer = PooledByteBufAllocator.DEFAULT.heapBuffer();
        printBuf2(pooledHeapBuffer);
        // 池化分配器-直接内存
        ByteBuf pooledDirectBuffer = PooledByteBufAllocator.DEFAULT.directBuffer();
        printBuf2(pooledDirectBuffer);

        log.info("=============Unpooled工具类================");

        // netty提供的工具类，底层调用的是UnpooledByteBufAllocator.DEFAULT
        ByteBuf buf1 = Unpooled.buffer();
        printBuf2(buf1);
        ByteBuf buf2 = Unpooled.directBuffer();
        printBuf2(buf2);

        log.info("=============默认分配器================");

        // 默认分配器，根据系统配置创建（默认都是池化的）
        ByteBuf buf3 = ByteBufAllocator.DEFAULT.buffer(); // 默认是直接内存directByDefault: true
        printBuf2(buf3);
        ByteBuf buf4 = ByteBufAllocator.DEFAULT.directBuffer();
        printBuf2(buf4);
        ByteBuf buf5 = ByteBufAllocator.DEFAULT.heapBuffer();
        printBuf2(buf5);

    }

    private void printBuf2(ByteBuf buf) {
        log.info("hasArray:{} isDirect:{} alloc:{}",
                buf.hasArray(),
                buf.isDirect(),
                buf.alloc());
    }

    @Test
    public void testHeapBuffer() {
        ByteBuf heapBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        heapBuf.writeBytes("hello".getBytes(StandardCharsets.UTF_8));

        if (heapBuf.hasArray()) {
            // 堆内存可直接读取
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            log.info("read: {}", new String(array, offset, length));
        }

    }

    @Test
    public void testDirectBuffer() {
        ByteBuf directBuffer = ByteBufAllocator.DEFAULT.directBuffer();
        directBuffer.writeBytes("hello".getBytes(StandardCharsets.UTF_8));
        // 直接内存
        if (!directBuffer.hasArray()) {
            // 直接内存不能直接读取，需要读取到堆区数组中
            byte[] bytes = new byte[directBuffer.readableBytes()];
            // 读指针不变
//            directBuffer.getBytes(directBuffer.readerIndex(), bytes);
            // 读指针变化
            directBuffer.readBytes(bytes);
            log.info("read: {} directBuffer:{}", new String(bytes), directBuffer);
        }
        directBuffer.release();
    }

    @Test
    public void arrayOffset() {
        byte[] bytes = "0123456789".getBytes(StandardCharsets.UTF_8);
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes, 2, 5);
        log.info("arrayOffset:{} readerIndex:{}", byteBuf.arrayOffset(), byteBuf.readerIndex());
        if (byteBuf.hasArray()) {
            // 堆内存可直接读取
            byte[] array = byteBuf.array();
            int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
            int length = byteBuf.readableBytes();
            log.info("read: {}", new String(array, offset, length));
        }
    }

    @Test
    public void arrayOffset2() {

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes("0123456789".getBytes(StandardCharsets.UTF_8));

        ByteBuf subBuf = byteBuf.slice(2, 5);
        log.info("arrayOffset:{} readerIndex:{}", subBuf.arrayOffset(), subBuf.readerIndex());
        if (subBuf.hasArray()) {
            // 堆内存可直接读取
            byte[] array = subBuf.array();
            int offset = subBuf.arrayOffset() + subBuf.readerIndex();
            int length = subBuf.readableBytes();
            log.info("read: {}", new String(array, offset, length));
        }
    }

    @Test
    public void testCompositeBuf() {
        // maxNumComponents只是初始化数量，超过这个大小会自动扩容
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer(2);
        compositeByteBuf.addComponent(Unpooled.wrappedBuffer("012".getBytes(StandardCharsets.UTF_8)));
        compositeByteBuf.addComponent(Unpooled.wrappedBuffer("345".getBytes(StandardCharsets.UTF_8)));
        compositeByteBuf.addComponent(Unpooled.wrappedBuffer("678".getBytes(StandardCharsets.UTF_8)));
        compositeByteBuf.addComponent(Unpooled.wrappedBuffer("9".getBytes(StandardCharsets.UTF_8)));
        // 共享内存的java缓冲区
        ByteBuffer subBuf = compositeByteBuf.nioBuffer(0, 5);
        log.info("subBuf:{}", new String(subBuf.array(), subBuf.position(), subBuf.limit()));
    }

    @Test
    public void reuseByteBuf() {
        // 组合缓冲区作用，复用headerBuf
        CompositeByteBuf compositeBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        ByteBuf headerBuf = Unpooled.copiedBuffer("aaa".getBytes(StandardCharsets.UTF_8));
        ByteBuf bodyBuf = Unpooled.copiedBuffer("012".getBytes(StandardCharsets.UTF_8));
        compositeBuf.addComponents(headerBuf, bodyBuf);
        sendMsg(compositeBuf);

        headerBuf.retain(); // 注释此行，compositeBuf.release()会释放headerBuf，导致后续无法读取
        compositeBuf.release();

        compositeBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        bodyBuf = Unpooled.copiedBuffer("345".getBytes(StandardCharsets.UTF_8));
        // 复用headerBuf
        compositeBuf.addComponents(headerBuf, bodyBuf);
        sendMsg(compositeBuf);
        compositeBuf.release();
    }

    public void sendMsg(CompositeByteBuf compositeByteBuf) {
        for (ByteBuf buf : compositeByteBuf) {
            while (buf.isReadable()) {
                byte[] bytes = new byte[buf.readableBytes()];
                buf.readBytes(bytes);
                log.info("read:{}", new String(bytes));
            }
        }
    }
}
