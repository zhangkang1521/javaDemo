package org.zk;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法区溢出：jdk6: -XX:PermSize=10M -XX:MaxPermSize=10M
 * 1.8中将方法区移到本地内存： -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
 */
public class MethodAreaOOM {

	public static void main(String[] args) {
		long count = 0;
		for (;;) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				@Override
				public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
					return methodProxy.invokeSuper(o, objects);
				}
			});
			enhancer.create();
			if (++count % 100 == 0) {
				System.out.println(count);
			}
		}

	}

	static class OOMObject {

		public void f() {
			System.out.println("f()");
		}
	}
}
