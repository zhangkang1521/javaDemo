package org.zk;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class ConnectionPoolTest {

    private static AtomicInteger success = new AtomicInteger(0);
    private static AtomicInteger fail = new AtomicInteger(0);
    private static  CountDownLatch countDownLatch = new CountDownLatch(10);
    static Semaphore semaphore = new Semaphore(3); // 控制并发数


    public static void main(String[] args) throws Exception {
        ConnectionPool connectionPool = new ConnectionPool(3);
        for (int i = 0; i < 10; i++) {
            new Thread(new ConnectionTask(connectionPool), "task" + i).start();
        }
        System.out.println("wait all end");
        countDownLatch.await();
        System.out.println("success:" + success);
        System.out.println("fail:" + fail);
    }

    static class ConnectionTask implements Runnable {

        ConnectionPool connectionPool;

        public ConnectionTask(ConnectionPool connectionPool) {
            this.connectionPool = connectionPool;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Connection conn = connectionPool.getConnection();
                System.out.println(Thread.currentThread() + "=>" + conn);
                if (conn != null) {
                    success.incrementAndGet();
                    try {
                        for (int j = 0; j < 100; j++) {
                            Statement stmt = conn.createStatement();
                            stmt.execute("insert into tb_user(username) values('zk')");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    fail.incrementAndGet();
                }
                connectionPool.releaseConnection(conn);
                semaphore.release();
            }
            countDownLatch.countDown();
        }
    }

}