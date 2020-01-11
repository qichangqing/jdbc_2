package com.atguigu1.transaction;

import com.atguigu1.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author qcq
 * @create 2020/1/11-12:23 下午
 */
public class TransactionTest {
    @Test
    public void testTransactionSelect() throws Exception {
            Connection connection = JDBCUtils.getConnecton();
            System.out.println(connection.getTransactionIsolation());
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            String sql="select user,password,balance from user_table where user=?";
            User user = getInstance(connection, User.class, sql, "CC");
            System.out.println(user);

    }
    @Test
    public void testTransactionUpdate() throws Exception {
        Connection connection = JDBCUtils.getConnecton();
        connection.setAutoCommit(false);
        String sql="update user_table set balance=? where user=?";
        update(sql,5000,"CC");
        Thread.sleep(15000);
        System.out.println("修改结束");
    }
    //查询一条记录version2.0 考虑事务
    public <T> T getInstance(Connection connection,Class<T> clazz,String sql,Object ...args){
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            if(resultSet.next()){
                T t = clazz.newInstance();
                for(int i=0;i<columnCount;i++){
                    Object object = resultSet.getObject(i + 1);
//                    String columnName=metaData.getColumnName(i+1);
                    String columnLabel=metaData.getColumnLabel(i+1);
                    Field field=t.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,object);
                }
                return t;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally{

            JDBCUtils.closeResource(null,preparedStatement,resultSet);
        }
        return null;
    }
    //测试
    @Test
    public void testUpdateTx(){
        Connection connecton = null;
        try {
            connecton = JDBCUtils.getConnecton();
            System.out.println(connecton.getAutoCommit());
            connecton.setAutoCommit(false);
            String sql1="update user_table set balance=balance-100 where user=?";
            updateForTx(connecton,sql1,"AA");
            System.out.println(10/0);
            String sql2="update user_table set balance=balance+100 where user=?";
            updateForTx(connecton,sql2,"BB");
            connecton.commit();
            System.out.println("转账成功");
        } catch (Exception e) {
            System.out.println("转账失败");
            try {
                if(connecton!=null)
                    connecton.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connecton,null);
        }


    }
    //测试
    @Test
    public void testUpdate() throws Exception {
        String sql1="update user_table set balance=balance-100 where user=?";
        update(sql1,"AA");
        System.out.println(10/0);
        String sql2="update user_table set balance=balance+100 where user=?";
        update(sql2,"BB");
        System.out.println("转账成功");

    }
    //通用增删改操作v2.0
    public int updateForTx(Connection connection,String sql,Object ...args){
        PreparedStatement preparedStatement=null;
        try {
            //预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            //执行修改
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            JDBCUtils.closeResource(null,preparedStatement);
        }
        return 0;
    }
    //通用增删改操作v1.0
    public int update(String sql,Object ...args){
        Connection connecton = null;
        PreparedStatement preparedStatement=null;
        try {
            //获取链接
            connecton = JDBCUtils.getConnecton();
            //预编译sql
            preparedStatement = connecton.prepareStatement(sql);
            //填充占位符
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            //执行修改
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            JDBCUtils.closeResource(connecton,preparedStatement);
        }
        return 0;
    }
}
