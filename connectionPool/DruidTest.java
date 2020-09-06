package xyz.mjkblog.connectionPool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--05--3:47 PM
 */
public class DruidTest {

    private static DataSource dataSource1;
    static{
        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            properties.load(resourceAsStream);

            dataSource1 = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws Exception {
        Connection connection = dataSource1.getConnection();
        return connection;
    }
}
