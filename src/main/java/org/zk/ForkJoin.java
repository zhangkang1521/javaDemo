package org.zk;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoin {
	public static ForkJoinPool forkJoinPool = new ForkJoinPool();

	public static void main(String[] args) throws Exception{
		Future<Integer> future = forkJoinPool.submit(new CountTask(1, 4));
		System.out.println(future.get());
	}

	static class CountTask extends RecursiveTask<Integer> {
		private int start;
		private int end;
		public static final int THRESHOLD = 2;


		public CountTask(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected Integer compute() {
			int sum = 0;
			boolean canCompute = (end - start) <= THRESHOLD;
			if (canCompute) {
				for (int i = start; i <= end; i++) {
					sum += i;
				}
			} else {
				int middle = (start + end) / 2;
				CountTask countTask1 = new CountTask(start, middle);
				CountTask countTask2 = new CountTask(middle + 1, end);
				countTask1.fork();
				countTask2.fork();
				int result1 = countTask1.join();
				int result2 = countTask2.join();
				sum = result1 + result2;
			}
			return sum;
		}

	}


}
