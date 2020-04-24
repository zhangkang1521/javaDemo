package org.zk;

public class ClinitTest {


	static int i = 1;

	static {
		i = 0;
	}




	public static void main(String[] args) {
		System.out.println(i);
	}
}
