package com.atguigu3.dao;

import com.atguigu2.bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @author qcq
 * @create 2020/1/11-7:44 下午
 */
public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO {
    @Override
    public void insert(Connection connection, Customer customer) {
        String sql="insert into customers(name,email,birth) values(?,?,?)";
        update(connection, sql,customer.getName(),customer.getEmail(),customer.getBirth());
    }

    @Override
    public void deleteById(Connection connection, int id) {
        String sql="delete from customers where id=?";
        update(connection,sql,id);
    }

    @Override
    public void update(Connection connection, Customer customer) {
        String sql="update customers set name=?,email=?,birth=? where id=?";
        update(connection,sql,customer.getName(),customer.getEmail(),customer.getBirth(),customer.getId());
    }

    @Override
    public Customer getCustomerById(Connection connection, int id) {
        String sql="select id,name,email,birth from customers where id=?";
        Customer customer = getInstance(connection, sql, id);
        return customer;
    }

    @Override
    public List<Customer> getAll(Connection connection) {
        String sql ="select id,name,email,birth from customers";
        List<Customer> customers = getForList(connection, sql);
        return customers;
    }

    @Override
    public long getCount(Connection connection) {
        String sql="select count(*) from customers";
        long count = getValue(connection, sql);
        return count;
    }

    @Override
    public Date getMaxBirth(Connection connection) {
        String sql="select max(birth) from customers";
        Date date = getValue(connection, sql);
        return date;
    }
}
