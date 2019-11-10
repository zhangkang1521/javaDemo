package org.zk;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MockTest {

    @Test
    public void mockTest() {
        // 全部模拟
        A mockA = mock(A.class);
        when(mockA.method1()).thenReturn(11);
        doCallRealMethod().when(mockA).method2(); // 指定调用实际方法
        // 不会调用实际方法
        assertEquals(11, mockA.method1());
        assertEquals(2, mockA.method2());
        assertEquals(0, mockA.method3());
    }

    @Test
    public void spyTest() {
        // 使用实际对象，实现部分模拟
        A a = new A();
        A spy = spy(a);
        when(spy.method1()).thenReturn(11);
        doReturn(33).when(spy).method3(); // 这种语法指定不调用实际方法
        System.out.println(spy.method1());
        System.out.println(spy.method2());
        System.out.println(spy.method3());
    }



    static class A {

        public int method1() {
            System.out.println("invoke method1");
            return 1;
        }

        public int method2() {
            System.out.println("invoke method2");
            return 2;
        }

        public int method3() {
            System.out.println("invoke method3");
            return 3;
        }
    }
}
