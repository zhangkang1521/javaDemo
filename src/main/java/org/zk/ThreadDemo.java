package org.zk;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThreadDemo {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		createBusyThread();
		br.readLine();
		createLockThread();
	}

	private static void createLockThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ThreadDemo threadDemo = new ThreadDemo();
				synchronized (threadDemo) {
					try {
						threadDemo.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "wait-thread").start();
	}

	private static void createBusyThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true);
			}
		}, "busy-thread").start();
	}
}
