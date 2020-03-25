package org.zk;

public class DeadLock {

	public static void main(String[] args) {
		Key keyA = new Key("a");
		Key keyB = new Key("b");
		new Thread(new LockAb(keyA, keyB), "abThread").start();
		new Thread(new LockAb(keyB, keyA), "baThread").start();
	}

	static class LockAb implements Runnable {

		private Key keyA;
		private Key keyB;

		public LockAb(Key keyA, Key keyB) {
			this.keyA = keyA;
			this.keyB = keyB;
		}

		@Override
		public void run() {
			synchronized (keyA) {
				System.out.println(Thread.currentThread() + " lock " + keyA);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (keyB) {

				}
			}
		}
	}

	static class Key {

		private String name;

		public Key(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

}
