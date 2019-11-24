package org.zk;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.Properties;

public class JndiTest {

    @Before
    public void before() throws Exception {
        DataSource dataSource = new MysqlDataSource();
        ((MysqlDataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/zk");
        ((MysqlDataSource) dataSource).setUser("root");
        ((MysqlDataSource) dataSource).setPassword("123456");

        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        properties.put(Context.URL_PKG_PREFIXES, "org.apache.naming");
        Context ctx = new InitialContext(properties);
        ctx.bind("zkDataSource", dataSource);


    }

    @Test
    public void testJndi() throws Exception{
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        properties.put(Context.URL_PKG_PREFIXES, "org.apache.naming");
        Context ctx = new InitialContext(properties);
        DataSource dataSource = (DataSource) ctx.lookup("zkDataSource");
        System.out.println(dataSource);
    }
}
