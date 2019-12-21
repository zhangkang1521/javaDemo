package org.zk;

import com.mysql.jdbc.JDBC4Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class StatementTest {

    Connection connection;

    @Before
    public void before() throws Exception {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zk", "root", "123456");
    }

    @After
    public void after() throws Exception {
        connection.close();
    }

    @Test
    public void testScript() throws Exception {
        // ;
        Statement statement = connection.createStatement();
        statement.execute("create table tb_user2 (  id int,  name varchar(20),  primary key(id))");
    }


    @Test
    public void testWrap() throws Exception {
//        Class clazz = Class.forName("com.mysql.jdbc.JDBC4Connection");
//        // Wrapper接口的作用：可以访问特定驱动扩展的方法
//        if (connection.isWrapperFor(clazz)) {
//            JDBC4Connection jdbc4Connection = (JDBC4Connection) connection.unwrap(clazz);
//            jdbc4Connection.getServerCharset();
//        }
    }

    @Test
    public void testGeneratedKey() throws Exception {
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate("insert into tb_user(username, age) values('zk', 10),('zx', 0)", Statement.RETURN_GENERATED_KEYS);
        System.out.println(result);
        ResultSet generatedKeys = statement.getGeneratedKeys();
        while (generatedKeys.next()) {
            System.out.println(generatedKeys.getInt(1));
        }
    }


    @Test
    public void testMoreElement() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zk?allowMultiQueries=true", "root", "123456");
        Statement statement = connection.createStatement();
        boolean result = statement.execute("select * from tb_user where username = 'zk'; select * from tb_user where username = 'zz'");
        do {
            System.out.println("============");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int age = resultSet.getInt("age");
                System.out.printf("username:%s, age:%d \n", username, age);
            }
        } while (statement.getMoreResults());
        connection.close();
    }

    @Test
    public void testJdbcBatch() throws Exception {
        Statement statement = connection.createStatement();
        for (int i = 0; i < 1000; i++) {
            statement.addBatch("insert into tb_user(username, age) values('zk" + i + "', 10)");
        }
        statement.executeBatch();
    }

    @Test
    public void testJdbcNoBatch() throws Exception {
        Statement statement = connection.createStatement();
        for (int i = 0; i < 1000; i++) {
            statement.execute("insert into tb_user(username, age) values('zk" + i + "', 10)");
        }
    }

    @Test
    public void testBatchInsert() throws Exception {
        // 单条批量插入性能最好
        Statement statement = connection.createStatement();
        StringBuilder sql = new StringBuilder("insert into tb_user(username, age) values");
        for (int i = 0; i < 1000; i++) {
            sql.append("('zk', 10),");
        }
        statement.execute(sql.substring(0, sql.length() - 1));
    }

    @Test
    public void testPreparedStatement() throws Exception {
        // 性能和Statement差不多???
        PreparedStatement pstmt = connection.prepareStatement("insert into tb_user(username, age) values(?, ?)");
        for (int i = 0; i < 1000; i++) {
            pstmt.setString(1, "zk" + i);
            pstmt.setInt(2, 10);
            pstmt.execute();
        }
    }

    @Test
    public void testSetObject() throws Exception {
        PreparedStatement pstmt = connection.prepareStatement("insert into tb_user(username, age) values(?, ?)");
        pstmt.setObject(1, "zk");
        pstmt.setObject(2, 18);
        pstmt.execute();
    }
}
