package org.zk;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Piped {

	public static void main(String[] args) throws Exception {
		PipedWriter out = new PipedWriter();
		PipedReader in = new PipedReader();
		in.connect(out);

		new Thread(new Task(in)).start();
		SleepUtils.second(1);

		out.write("abc");

	}

	static class Task implements Runnable {

		PipedReader in;

		public Task(PipedReader in) {
			this.in = in;
		}

		@Override
		public void run() {
			try {
				int ch = -1;
				while ((ch = in.read()) != -1) {
					System.out.println(ch);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
