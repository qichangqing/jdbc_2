package com.atguigu4.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author qcq
 * @create 2020/1/11-11:07 下午
 */
public class JDBCUtils {
    private static ComboPooledDataSource cpds=new ComboPooledDataSource("hellc3p0");
    public static Connection getConnection() throws SQLException {
        Connection connection = cpds.getConnection();
        return connection;
    }
    private static DataSource source;
    static {
        InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        Properties properties=new Properties();
        try {
            properties.load(is);
            source = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection2() throws SQLException {
        Connection connection = source.getConnection();
        return connection;
    }
    private static DataSource source1;
    static {
        InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        Properties properties=new Properties();
        try {
            properties.load(is);
            source1= DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection3() throws SQLException {
        Connection connection = source1.getConnection();
        return connection;
    }
    public static void closeResource(Connection connection, PreparedStatement statement){
        try {
            if(connection!=null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(statement!=null){
                statement.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet){
        try {
            if(connection!=null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(statement!=null){
                statement.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(resultSet!=null)
                resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void closeResource1(Connection connection, PreparedStatement statement, ResultSet resultSet){
        DbUtils.closeQuietly(connection);
        DbUtils.closeQuietly(statement);
        DbUtils.closeQuietly(resultSet);
    }
}
