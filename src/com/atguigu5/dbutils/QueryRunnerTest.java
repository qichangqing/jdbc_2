package com.atguigu5.dbutils;

import com.atguigu2.bean.Customer;
import com.atguigu4.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

/**
 * @author qcq
 * @create 2020/1/12-12:46 上午
 */
public class QueryRunnerTest {
    @Test
    public void testInsert(){
        Connection connection3 = null;
        try {
            QueryRunner runner=new QueryRunner();
            connection3 = JDBCUtils.getConnection3();
            String sql="insert into customers(name,email,birth) values(?,?,?)";
            int count = runner.update(connection3, sql, "蔡徐坤", "caixukun@126.com", "1997-09-07");
            System.out.println("添加了"+count+"条记录");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection3,null);
        }
    }
    @Test
    public void testQuery1(){
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection3();
            QueryRunner runner=new QueryRunner();
            String sql="select name,email,birth from customers where name=?";
            BeanHandler<Customer> handler=new BeanHandler<>(Customer.class);
            Customer customer = runner.query(connection, sql, handler, "蔡徐坤");
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection,null);
        }
    }
    @Test
    public void testQuery2(){
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection3();
            QueryRunner runner=new QueryRunner();
            String sql="select name,email,birth from customers where id<?";
            BeanListHandler<Customer> handler=new BeanListHandler<>(Customer.class);
            List<Customer> query = runner.query(connection, sql, handler, 8);
            query.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection,null);
        }
    }
    @Test
    public void testQuery3(){
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection3();
            QueryRunner runner=new QueryRunner();
            String sql="select count(*) from customers";
            ScalarHandler handler=new ScalarHandler();
            long count = (long)runner.query(connection, sql, handler);
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection,null);
        }
    }
    @Test
    public void testQuery4(){
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection3();
            QueryRunner runner=new QueryRunner();
            String sql="select max(birth) from customers";
            ScalarHandler handler=new ScalarHandler();
            Date date = (Date)runner.query(connection, sql, handler);
            System.out.println(date);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection,null);
        }
    }
    @Test
    public void testQuery5(){
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection3();
            QueryRunner runner=new QueryRunner();
            String sql="select id,name,email,birth from customers where id=?";
            ResultSetHandler<Customer> handler =new ResultSetHandler<Customer>() {
                @Override
                public Customer handle(ResultSet rs) throws SQLException {
                    if(rs.next()){
                        int id=rs.getInt("id");
                        String name=rs.getString("name");
                        String email=rs.getString("email");
                        Date date=rs.getDate("birth");
                        return new Customer(id,name,email,date);
                    }
                    return null;
                }
            };
            Customer customer = runner.query(connection, sql, handler,27);
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection,null);
        }
    }
}
