package com.atguigu4.connection;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author qcq
 * @create 2020/1/12-12:17 上午
 */
public class DruidTest {
    @Test
    public void testGetConnection() throws Exception {
        InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        Properties properties=new Properties();
        properties.load(is);
        DataSource dataSource= DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
