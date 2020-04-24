package org.zk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {

	interface IHello {
		void sayHello();
	}

	static class Hello implements IHello {

		@Override
		public void sayHello() {
			System.out.println("hello");
		}
	}

	static class DynamicProxy implements InvocationHandler {

		private Object target;

		public DynamicProxy(Object target) {
			this.target = target;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("---");
			return method.invoke(target, args);
		}
	}

	public static void main(String[] args) {
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		IHello hello = new Hello();
		IHello proxy = (IHello)Proxy.newProxyInstance(hello.getClass().getClassLoader(), new Class[] {IHello.class}, new DynamicProxy(hello));
		proxy.sayHello();
		System.out.println(proxy.getClass());
		// $Proxy0.class 中写道
//		public final void sayHello() {
//			try {
				// h即InvocationHandler的实例
//				this.h.invoke(this, m3, null);
//				return;
//			} catch (Error|RuntimeException error) {
//				throw null;
//			} catch (Throwable throwable) {
//				throw new UndeclaredThrowableException(throwable);
//			}
//		}
	}
}
