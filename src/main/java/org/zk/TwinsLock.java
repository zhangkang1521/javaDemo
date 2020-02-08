package org.zk;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 允许2个线程同时获取的锁
 */
public class TwinsLock implements Lock {

	private static class Sync extends AbstractQueuedSynchronizer {

		Sync() {
			setState(2);
		}

//		@Override
//		protected boolean isHeldExclusively() {
//			return getState() == 1;
//		}

		@Override
		protected int tryAcquireShared(int arg) {
			for (;;) { // 多个线程可能cas失败，重试
				int current = getState();
				int newState = current - arg;
				if (newState < 0 || compareAndSetState(current, newState)) {
					return newState;
				}
			}
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			for (;;) {
				int current = getState();
				int newState = current + arg;
				if (compareAndSetState(current, newState)) {
					return true;
				}
			}
		}

		Condition newCondition() {
			return new ConditionObject();
		}
	}

	private final Sync sync = new Sync();

	@Override
	public void lock() {
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireSharedInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquireShared(1) > 0;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		 sync.releaseShared(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}


	public Collection<Thread> getQueuedThreads(){
		return sync.getQueuedThreads();
	}
}
