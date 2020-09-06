package xyz.mjkblog.connection;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--03--7:12 PM
 */
public class ConnectionTest {

    @Test
    public void testConnection1() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/JDBCLearning?serverTimezone=UTC";

        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password","123456Asdfgh");

        Connection conn = driver.connect(url, info);

        System.out.println(conn);
    }

    @Test
    public void testConnection2() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        //使用反射获得实现类对象，增加可修改性
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver)clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/JDBCLearning?serverTimezone=UTC";

        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password","123456Asdfgh");

        Connection conn = driver.connect(url, info);

        System.out.println(conn);
    }

    @Test
    public void testConnection3() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        //使用反射获得实现类对象
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver)clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/JDBCLearning?serverTimezone=UTC";
        String user = "root";
        String password = "123456Asdfgh";

        //注册驱动
        DriverManager.registerDriver(driver);

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    public void testConnection4() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String url = "jdbc:mysql://localhost:3306/JDBCLearning";
        String user = "root";
        String password = "123456Asdfgh";

        //加载驱动，对应的静态代码块会被执行
        Class.forName("com.mysql.cj.jdbc.Driver");
//        Driver driver = (Driver)clazz.newInstance();
//        //注册驱动
//        DriverManager.registerDriver(driver);

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    @Test
    public void testConnection5() throws IOException, ClassNotFoundException, SQLException {
        //读取配置文件的信息
        //用户自定义类获取的是系统类加载器
        InputStream inputStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String url=properties.getProperty("url");
        String driverClass=properties.getProperty("driverClass");

        //加载驱动
        Class.forName(driverClass);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
}
