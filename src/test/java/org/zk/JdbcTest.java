package org.zk;

import com.mysql.jdbc.JDBC4Connection;
import com.mysql.jdbc.jdbc2.optional.JDBC4ConnectionWrapper;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcTest {

    @Test
    public void test1() throws Exception{
//        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zk", "root", "123456");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tb_user");
        while(resultSet.next()) {
            String username = resultSet.getString("username");
            int age = resultSet.getInt("age");
            System.out.printf("username:%s, age:%d \n", username, age);
        }
        connection.close();
    }

    @Test
    public void testDataSource() throws Exception {
        DataSource dataSource = new MysqlDataSource();
        ((MysqlDataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/zk");
        ((MysqlDataSource) dataSource).setUser("root");
        ((MysqlDataSource) dataSource).setPassword("123456");
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tb_user");
        while(resultSet.next()) {
            String username = resultSet.getString("username");
            int age = resultSet.getInt("age");
            System.out.printf("username:%s, age:%d \n", username, age);
        }
        connection.close();
    }


}
