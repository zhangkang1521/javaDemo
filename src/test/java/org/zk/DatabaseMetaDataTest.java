package org.zk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseMetaDataTest {
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
    public void testGetInfo() throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println(metaData.getURL());
        System.out.println(metaData.getUserName());
        System.out.println(metaData.getDatabaseProductName());
        System.out.println(metaData.getDatabaseProductVersion());
        System.out.println(metaData.getDriverMajorVersion());
        System.out.println(metaData.getDriverMinorVersion());
        System.out.println(metaData.getSchemaTerm());
        System.out.println(metaData.getCatalogTerm());
        System.out.println(metaData.getProcedureTerm());
        System.out.println(metaData.usesLocalFilePerTable());
        System.out.println(metaData.usesLocalFiles());
        System.out.println(metaData.getSQLKeywords());

        System.out.println(metaData.getMaxRowSize());
        System.out.println(metaData.getMaxTablesInSelect());
        System.out.println(metaData.getMaxColumnsInTable());
        System.out.println("maxConnections:" + metaData.getMaxConnections());
        System.out.println(metaData.getResultSetHoldability());
        // Connection.TRANSACTION_READ_COMMITTED
        System.out.println("默认事务隔离级别：" + metaData.getDefaultTransactionIsolation());
    }
}
