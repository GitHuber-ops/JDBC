package xyz.mjkblog.preparedStatement.crud;

import org.junit.Test;
import xyz.mjkblog.connection.ConnectionTest;
import xyz.mjkblog.util.JDBCUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--04--9:24 AM
 */
public class PreparedStatementUpdateTest {

    public void testCommonUpdate() {
        String sql = "update customers set name=? where id=?";
        update(sql, "ysz003", "ysz002");
    }

    //通用增删改操作
    public void update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //获取连接
            connection = JDBCUtils.getConnection();
            //预编译SQL语句
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);//数据的下标是从1开始的哦
            }
            //执行
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //资源的关闭
            JDBCUtils.closeResource(connection, preparedStatement);
        }
    }

    //向custom表中添加一个数据
    @Test
    public void testInsert() throws IOException, ClassNotFoundException, SQLException, ParseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //读取配置文件的信息
            InputStream inputStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            //加载驱动
            Class.forName(driverClass);

            //获取连接
            connection = DriverManager.getConnection(url, user, password);
            //System.out.println(connection);

            //预编译sql语句，返回PreparedStatement实例
            String sql = "insert into customers(name,email,birth) values (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            //填充占位符,注意索引是从1开始
            preparedStatement.setString(1, "ysz001");
            preparedStatement.setString(2, "ysz001@ysz.com");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = simpleDateFormat.parse("2000-01-01");
            preparedStatement.setDate(3, new Date(date.getTime()));

            //执行SQL
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testUpdate() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //获取数据库连接
            connection = JDBCUtils.getConnection();

            //预编译SQL，返回PreparedStatement实例
            String sql = "update customers set name=? where id=?";
            preparedStatement = connection.prepareStatement(sql);

            //填充占位符
            preparedStatement.setObject(1, "ysz002");
            preparedStatement.setObject(2, "19");

            //执行
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //资源的关闭
            JDBCUtils.closeResource(connection, preparedStatement);
        }
    }
}
