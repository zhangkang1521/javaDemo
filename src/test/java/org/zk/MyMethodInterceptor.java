package org.zk;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 6/2/2018.
 */
public class MyMethodInterceptor implements MethodInterceptor {

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("=== start ===");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("=== end ===");
        return result;
    }
}
