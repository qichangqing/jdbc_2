package com.atguigu3.junit;

import com.atguigu1.util.JDBCUtils;
import com.atguigu2.bean.Customer;
import com.atguigu3.dao.CustomerDAOImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author qcq
 * @create 2020/1/11-8:14 下午
 */
public class CustomerDAOImplTest {
    private CustomerDAOImpl customerDAO=new CustomerDAOImpl();
    @Test
    public void testInsert() {
        Connection connecton = null;
        try {
            connecton = JDBCUtils.getConnecton();
            Customer customer=new Customer(1,"于小飞","xiaofei@126.com",new Date(8728323728L));
            customerDAO.insert(connecton,customer);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,null);
        }
    }
    @Test
    public void testDeleteById(){
        Connection connecton = null;
        try {
            connecton = JDBCUtils.getConnecton();
            customerDAO.deleteById(connecton,13);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,null);
        }
    }
    @Test
    public void testUpdate(){
        Connection connecton = null;
        try {
            connecton = JDBCUtils.getConnecton();
            Customer customer=new Customer(18,"贝多芬","beiduofen@126.com",new Date(98349229L));
            customerDAO.update(connecton,customer);
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,null);
        }
    }
    @Test
    public void testGetCustomerById(){
        Connection connecton = null;
        try {
            connecton = JDBCUtils.getConnecton();
            Customer customer = customerDAO.getCustomerById(connecton, 19);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,null);
        }
    }
    @Test
    public void testGetAll(){
        Connection connecton = null;
        try {
            connecton = JDBCUtils.getConnecton();
            List<Customer> list = customerDAO.getAll(connecton);
            list.forEach(System.out::println);
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,null);
        }
    }
    @Test
    public void testGetCount(){
        Connection connecton = null;
        try {
            connecton = JDBCUtils.getConnecton();
            long count = customerDAO.getCount(connecton);
            System.out.println("总个数为："+count);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,null);
        }
    }
    @Test
    public void testGetMaxBirth(){
        Connection connecton = null;
        try {
            connecton = JDBCUtils.getConnecton();
            java.util.Date maxBirth = customerDAO.getMaxBirth(connecton);
            System.out.println("最大生日为："+maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,null);
        }
    }
}
