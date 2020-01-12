package org.zk;

public class ThreadLocalParent {

	static ThreadLocal<String> loginUser = new ThreadLocal();

	public static void main(String[] args) {
		loginUser.set("zk");
		new Thread(new ChildTask()).start();
	}

	static class ChildTask implements Runnable {
		@Override
		public void run() {
			System.out.println(loginUser.get());
		}
	}
}
