package org.zk;

public class NotInitialization {

	public static void main(String[] args) {
//		ArrayDemo[] arr = new ArrayDemo[1];
//		// [Lorg.zk.ArrayDemo 由虚拟机生成的类
//		System.out.println(arr.getClass());

		System.out.println(ConstClass.TEST); // "test"会存到常量池
	}
}

class ArrayDemo {

	static {
		System.out.println("定义数组不会初始化");
	}
}

class ConstClass {
	public static final String TEST = "test";

	static {
		System.out.println("常量传播优化");
	}
}
