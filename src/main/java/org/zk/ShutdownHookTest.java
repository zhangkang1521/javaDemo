package org.zk;

public class ShutdownHookTest {

	public static void main(String[] args) throws Exception {
		System.out.println("--- try kill -15 pid to kill me");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("xxx");
			}
		});
		System.in.read();
	}
}
