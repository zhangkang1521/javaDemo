package org.zk;

import java.util.concurrent.atomic.*;

public class AtomicTest {

	static User user = new User(1, "zk");
	static AtomicStampedReference<User> atomicMarkableReference = new AtomicStampedReference<>(user, 0);

	public static void main(String[] args) {
		atomicMarkableReference.compareAndSet(user, new User(2, "zx"), 0, 1);

		System.out.println(atomicMarkableReference.getReference());
	}

	static class User {
		volatile int id;
		private String username;

		public User(int id, String username) {
			this.id = id;
			this.username = username;
		}

		@Override
		public String toString() {
			return "id:" + id + ", username:" + username;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}
}
