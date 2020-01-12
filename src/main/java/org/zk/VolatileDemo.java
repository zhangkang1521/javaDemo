package org.zk;

public class VolatileDemo {

	public static void main(String[] args) {
		CountTask countTask = new CountTask();
		Thread t1 = new Thread(countTask);
		t1.start();
		SleepUtils.second(2);
		countTask.cancel();
		SleepUtils.second(2);

		CountTask2 countTask2 = new CountTask2();
		Thread t2 = new Thread(countTask2);
		t2.start();
		SleepUtils.second(2);
		countTask2.cancel();
		SleepUtils.second(2);

		System.out.println(countTask.count);
		System.out.println(countTask2.count);
	}

	static class CountTask implements Runnable {

		private long count;
		private volatile boolean cancel = false;

		@Override
		public void run() {
			while(!cancel) {
				count++;
			}
		}

		public void cancel() {
			this.cancel = true;
		}
	}

	static class CountTask2 implements Runnable {

		private long count;
		private boolean cancel = false;

		@Override
		public void run() {
			while(!cancel) {
				count++;
			}
		}

		public void cancel() {
			this.cancel = true;
		}
	}
}
