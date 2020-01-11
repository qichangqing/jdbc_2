package com.atguigu3.dao;

import com.atguigu2.bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @author qcq
 * @create 2020/1/11-7:21 下午
 */
public interface CustomerDAO {
    void insert(Connection connection, Customer customer);
    void deleteById(Connection connection, int id);
    void update(Connection connection, Customer customer);
    Customer getCustomerById(Connection connection, int id);
    List<Customer> getAll(Connection connection);
    long getCount(Connection connection);
    Date getMaxBirth(Connection connection);
}
