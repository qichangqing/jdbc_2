package com.atguigu4.connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author qcq
 * @create 2020/1/11-11:24 下午
 */
public class DBCPTest {
    @Test
    public void testGetConnection() throws SQLException {
        BasicDataSource source=new BasicDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql:///test");
        source.setUsername("root");
        source.setPassword("root");
        source.setInitialSize(10);
        source.setMaxActive(10);
        Connection connection = source.getConnection();
        System.out.println(connection);

    }
    @Test
    public void testGetConnection1() throws Exception {
        InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        Properties properties=new Properties();
        properties.load(is);
        DataSource source = BasicDataSourceFactory.createDataSource(properties);
        Connection connection = source.getConnection();
        System.out.println(connection);

    }
}
