package org.zk;

public class InvokeMethod {

	// invokestatic
	public static void sayHello() {

	}

	public void hiPrivate() {

	}

	public static void main(String[] args) {
		InvokeMethod.sayHello();
		new Sub().hiPrivate();
	}

	static class Sub extends InvokeMethod {

	}
}
