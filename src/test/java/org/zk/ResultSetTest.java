package org.zk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class ResultSetTest {

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
    public void updateResultSet() throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        // mysql驱动只支持TYPE_SCROLL_INSENSITIVE？
        System.out.println("TYPE_FORWARD_ONLY:" + metaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
        System.out.println("TYPE_SCROLL_INSENSITIVE:" + metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
        System.out.println("TYPE_SCROLL_SENSITIVE:" + metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from tb_user");
        rs.absolute(3);
        rs.updateString("username", "123");
        rs.updateRow();
    }

    @Test
    public void deleteResultSet() throws Exception {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from tb_user");
        rs.absolute(3);
        rs.deleteRow();
    }

    @Test
    public void insertResultSet() throws Exception {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from tb_user");
        rs.moveToInsertRow();
        rs.updateString(2, "zk");
        rs.insertRow();
    }
}
