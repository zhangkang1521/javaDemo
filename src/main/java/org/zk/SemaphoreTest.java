package org.zk;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	static Semaphore semaphore = new Semaphore(2, true);

	public static void main(String[] args) throws Exception{
		semaphore.acquire();
		semaphore.acquire();
		semaphore.acquire();
		System.out.println("ok");
	}


}
