package xyz.mjkblog.preparedStatement.crud;

import org.junit.Test;
import xyz.mjkblog.bean.Customer;
import xyz.mjkblog.util.JDBCUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Date;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--04--12:32 PM
 */
public class CustomerForQuery {
    @Test
    public void testQuery1() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id =?";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            //判断结果集中是否还有数据
            if (resultSet.next()) {
                //获取结果集中的各个字段
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                ////使用数组封装数据
                //Object[] data = {id, name, email, birth};

                //将数据封装为一个对象
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
    }

    public Customer queryForCustomers(String sql,Object... args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();

            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery();

            //获取结果的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();

            //可以通过元数据获取结果的列数
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()) {
                Customer customer = new Customer();
                //处理某一行数据
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = metaData.getColumnName(i + 1);

                    //给customer的某一属性赋值为value，**反射**
                    Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(customer, value);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }
}
