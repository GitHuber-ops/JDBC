package xyz.mjkblog.preparedStatement.crud;

import org.junit.Test;
import xyz.mjkblog.bean.Order;
import xyz.mjkblog.util.JDBCUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--04--3:11 PM
 */
public class OrderQuery {

    @Test
    public Order orderForQuery(String sql,Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            //获取结果集
            resultSet = preparedStatement.executeQuery();
            //获取元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取列数
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()) {
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    //获取每个列的列值
                    Object value = resultSet.getObject(i + 1);
                    //获取每个列的列名
                    //getColumnName():获取列的列名
                    //getColumnLabel():获取列的别名
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    //通过反射将指定名的属性赋值为指定值
                    Field declaredField = Order.class.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(order, value);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }
}
