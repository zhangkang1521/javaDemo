package org.zk;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProfileCpuTest {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		test();
	}

	private static void test() throws Exception {
		Thread.sleep(200);
		test1();
		test2();
	}

	private static void test1() throws Exception{
		Thread.sleep(10);
	}

	private static void test2() throws Exception {
		Thread.sleep(20);
		test22();
	}

	private static void test22() throws Exception {
		Thread.sleep(30);
	}
}
