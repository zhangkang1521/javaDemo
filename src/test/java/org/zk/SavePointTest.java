package org.zk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Savepoint;
import java.sql.Statement;

public class SavePointTest {

    Connection connection;

    @Before
    public void before() throws Exception {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zk", "root", "123456");
    }

    @After
    public void after() throws Exception {
        connection.close();
    }

    // 嵌套事务
    @Test
    public void savePoint() throws Exception {
        connection.setAutoCommit(false);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("insert into tb_user(username) values('zx')");
        Savepoint savepoint1 = connection.setSavepoint("point1");
        stmt.executeUpdate("insert into tb_user(username) values('zx2')");
        Savepoint savepoint2 = connection.setSavepoint("point2");
        connection.rollback(savepoint1); // 回退到point1,zx2不会存储
        connection.commit();
    }
}
