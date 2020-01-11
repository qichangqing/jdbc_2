package com.atguigu2.dao;

import com.atguigu1.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qcq
 * @create 2020/1/11-6:53 下午
 */
public abstract class BaseDAO<T> {
    private Class<T> clazz=null;
    {   //子类获取父类的范型参数类
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType type=(ParameterizedType)genericSuperclass;
        Type[] actualTypeArguments = type.getActualTypeArguments();
        clazz=(Class<T>) actualTypeArguments[0];
    }
    //通用增删改操作v2.0
    public int update(Connection connection, String sql, Object ...args){
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
    //查询一条记录version2.0 考虑事务
    public  T getInstance(Connection connection,String sql,Object ...args){
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
    //查询多条记录2.0考虑事务
    public List<T> getForList(Connection connection, String sql, Object ...args){
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            List<T> list=new ArrayList<T>();
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()){
                T t = clazz.newInstance();
                for(int i=0;i<columnCount;i++){
                    Object object = resultSet.getObject(i + 1);
//                    String columnName=metaData.getColumnName(i+1);
                    String columnLabel=metaData.getColumnLabel(i+1);
                    Field field=t.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,object);
                }
                list.add(t);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            JDBCUtils.closeResource(null,preparedStatement,resultSet);
        }
        return null;
    }
    //返回查询特殊值的方法
    public <E> E getValue(Connection connection,String sql,Object ...args) {
        PreparedStatement preparedStatement= null;
        ResultSet resultSet=null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return (E)resultSet.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(null,preparedStatement,resultSet);
        }
        return null;
    }
}
