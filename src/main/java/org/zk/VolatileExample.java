package org.zk;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class VolatileExample {
	int[] arr = new int[1000];
	boolean flag = false;

	static VolatileExample example = new VolatileExample();

	public void write() {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = 1;
		}
		flag = true;
	}

	public void reader() {
		if (flag) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != 1) {
					System.out.println("xxxx");
				}
			}
		}
	}

	public static void main(String[] args) {
		List<Thread> list = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			list.add(new Thread(new Reader()));
			if (i == 100) {
				list.add(new Thread(new Writer()));
			}
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).start();
		}
	}

	static class Reader implements Runnable {
		@Override
		public void run() {
			example.reader();
		}
	}

	static class Writer implements Runnable {
		@Override
		public void run() {
			example.write();
		}
	}
}
