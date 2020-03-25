package org.zk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OOMObject {

	private byte[] bytes = new byte[1024 * 1024/4]; // 1M

	public static void main(String[] args) throws Exception {
		List<OOMObject> list = new ArrayList<OOMObject>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		System.out.println("start");
		for (int i = 0; i < 79; i++) {
			Thread.sleep(200);
			list.add(new OOMObject());
		}
		br.readLine();
	}
}
