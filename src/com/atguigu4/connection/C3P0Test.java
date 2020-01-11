package com.atguigu4.connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author qcq
 * @create 2020/1/11-10:26 下午
 */
public class C3P0Test {
    @Test
    public void testGetConnection() throws Exception {
        ComboPooledDataSource dpds=new ComboPooledDataSource();
        dpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dpds.setUser("root");
        dpds.setPassword("root");
        dpds.setDriverClass("com.mysql.jdbc.Driver");
        dpds.setInitialPoolSize(10);
        Connection connection = dpds.getConnection();
        System.out.println(connection);
        //销毁连接池
//        DataSources.destroy(dpds);
    }
    @Test
    public void testGetConnection1() throws SQLException {
        ComboPooledDataSource cpds=new ComboPooledDataSource("hellc3p0");
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }
}
