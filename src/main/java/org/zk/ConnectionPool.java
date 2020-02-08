package org.zk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initSize) {
        for (int i = 0; i < initSize; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zk", "root", "123456");
                pool.add(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        synchronized (pool) {
            long timeout = 200;
            long future = System.currentTimeMillis() + timeout;
            long remain = timeout;
            while (pool.isEmpty() && remain > 0) {
                try {
                    pool.wait(remain);
                    remain = future - System.currentTimeMillis();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!pool.isEmpty()) {
                return pool.pollFirst();
            } else {
                return null;
            }
        }
    }

    public void releaseConnection(Connection connection) {
        synchronized (pool) {
            if (connection != null) {
                pool.add(connection);
                pool.notifyAll();
            }
        }
    }
}
