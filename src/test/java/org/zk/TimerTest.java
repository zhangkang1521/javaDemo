package org.zk;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println(new Date() + "task 1 run");
//				SleepUtils.second(3);
//				System.out.println(new Date() + "task 1 run end");
				throw new RuntimeException("xxx");
			}
		}, 1000);
		System.out.println(new Date());
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println(new Date() + "task 2 run");
			}
		}, 3000); // 因为只有1个线程，延迟3s实际延迟了4s
	}
}
