package org.zk.consumer;

import org.zk.SleepUtils;

import java.util.ArrayList;
import java.util.List;

public class Yuna1 {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		List<String> emails = extractEmail();
		emailToConfluence(emails);
		System.out.println(System.currentTimeMillis() - start);
	}

	private static void emailToConfluence(List<String> emails) {
		for (String str : emails) {
			System.out.println(str);
			SleepUtils.millisecond(5);
		}
	}

	private static List<String> extractEmail() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			list.add(String.valueOf(i));
			System.out.println("add:" + i);
			SleepUtils.millisecond(5);
		}
		return list;
	}
}
