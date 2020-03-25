package org.zk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * visualvm 打印add方法的参数
 */
public class BTrace {

	public int add(int a, int b) {
		return a + b;
	}
	public void run(){
		int a = (int) (Math.random() * 1000);
		int b = (int) (Math.random() * 1000);
		System.out.println(add(a, b));
	}
	public static void main(String[] args) throws IOException {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		BTrace bTraceTest = new BTrace();
		bReader.readLine();
		bTraceTest.run();
	}

}

//	@OnMethod(
//			clazz="org.zk.BTrace",
//			method="add",
//			location=@Location(Kind.RETURN)
//	)
//	public static void func(@Self org.zk.BTrace instance ,int a,int b,@Return int result){
//		println("调用堆栈");
//		jstack();
//		println(strcat("方法参数A：",str(a)));
//		println(strcat("方法参数B：",str(b)));
//		println(strcat("方法结果：",str(result)));
//
//	}
