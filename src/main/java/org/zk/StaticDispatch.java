package org.zk;

public class StaticDispatch {
	static abstract class Human {

	}

	static class Man extends Human{

	}

	static class Woman extends Human {

	}

	public void sayHello(Human human) {
		System.out.println("human");
	}

	public void sayHello(Man human) {
		System.out.println("man");
	}

	public void sayHello(Woman human) {
		System.out.println("woman");
	}

	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		StaticDispatch dispatch = new StaticDispatch();
		// 重载的判断依据为静态类型而不是实际类型
		dispatch.sayHello(man);
		dispatch.sayHello(woman);

	}
}
