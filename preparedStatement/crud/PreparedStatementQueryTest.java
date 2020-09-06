package xyz.mjkblog.preparedStatement.crud;

import org.junit.Test;
import xyz.mjkblog.bean.Customer;
import xyz.mjkblog.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--04--4:24 PM
 */
public class PreparedStatementQueryTest {

    //通用的查询操作
    public <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
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

            //用于保存结果集合
            ArrayList<T> list = new ArrayList<>();

            while (resultSet.next()) {
                T t = clazz.newInstance();
                //处理某一行数据
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = metaData.getColumnName(i + 1);

                    //给customer的某一属性赋值为value，**反射**
                    Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(t, value);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }

    //单一对象获取的通用方法
    @Test
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
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
                T t = clazz.newInstance();
                //处理某一行数据
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = metaData.getColumnName(i + 1);

                    //给customer的某一属性赋值为value，**反射**
                    Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(t, value);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }
}
