package org.zk;


import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test1() throws Exception{
        ProxyFactory enhancer = new ProxyFactory();
        enhancer.setSuperclass(UserService.class);
        Object obj = enhancer.create(null, null);
        ((Proxy) obj).setHandler(new MethodHandler() {
            public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
                System.out.println("=========");
                return proceed.invoke(self, args);
            }
        });
        String str = ((UserService)obj).sayHello("zhangxuan");
        System.out.println(str);
    }
    
}
