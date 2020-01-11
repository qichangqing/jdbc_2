package com.atguigu1.transaction;

import com.atguigu1.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author qcq
 * @create 2020/1/11-12:17 下午
 */
public class ConnectionTest {
    @Test
    public void testGetConnection() throws Exception {
        Connection connecton = JDBCUtils.getConnecton();
        System.out.println(connecton);
    }
}
