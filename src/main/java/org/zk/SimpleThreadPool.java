package org.zk;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SimpleThreadPool implements ThreadPool {

	// 任务队列
	private List<Runnable> jobs = new LinkedList<>();

	private List<Runnable> works = new ArrayList<>();

	public SimpleThreadPool(int initSize) {
		for (int i = 0; i < initSize; i++) {
			Worker worker = new Worker();
			works.add(worker);
			new Thread(worker, "work-" + i).start();
		}
	}

	@Override
	public void execute(Runnable runnable) {
		synchronized (jobs) {
			jobs.add(runnable);
			jobs.notify();
			System.out.println(new Date() + " add a job");
		}

	}

	class Worker implements Runnable {

		@Override
		public void run() {
			while(true) {
				synchronized (jobs) {
					while (jobs.isEmpty()) {
						try {
							System.out.println(new Date() + " " + Thread.currentThread() + " wait...");
							jobs.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(new Date() + " " + Thread.currentThread() + " run");
					Runnable job = jobs.remove(0);
					try {
						job.run();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
