package xyz.mjkblog.connectionPool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import java.sql.Connection;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--05--1:57 PM
 */
public class C3P0Test {

    @Test
    public void testGetConnection1() throws Exception {
        //简单测试
        //获取数据库连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/JDBCLearning?serverTimezone=UTC");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("123456Asdfgh");
        //设置初始数据库连接池中连接数
        comboPooledDataSource.setInitialPoolSize(10);

        Connection connection = comboPooledDataSource.getConnection();
        System.out.println("connection = " + connection);
        //销毁连接池
        DataSources.destroy(comboPooledDataSource);
    }



    //获取数据库连接池
    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("helloc3p0");

    //使用文件进行配置
    @Test
    public void testGetConnection2() throws Exception {
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println("connection = " + connection);
    }
}
