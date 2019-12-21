package org.zk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class TypeTest {

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
    public void testSetParam() throws Exception {
        PreparedStatement pstmt = connection.prepareStatement("insert into tb_user(username, status) values(?, ?)");
        pstmt.setString(1, "zk");
        // mybatis用TypeHandle，所有都用setObject不合适
        pstmt.setObject(2, UserStatus.ENABLE);
        pstmt.execute();
    }

    @Test
    public void testGetResult() throws Exception {
        PreparedStatement pstmt = connection.prepareStatement("select * from tb_user where id = 1");
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metaData.getColumnClassName(i));
        }
    }
}
