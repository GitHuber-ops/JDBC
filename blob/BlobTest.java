package xyz.mjkblog.blob;

import org.junit.Test;
import xyz.mjkblog.bean.Customer;
import xyz.mjkblog.util.JDBCUtils;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--04--7:31 PM
 */
public class BlobTest {
    //向数据表customers中插入Blob类型的字段
    @Test
    public void testInsert() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setObject(1, "ysz003");
        preparedStatement.setObject(2, "ysz003@ysz.com");
        preparedStatement.setObject(3, "2000-01-01");
        FileInputStream fileInputStream = new FileInputStream(new File("src/main/resources/testPhoto.jpg"));
        preparedStatement.setObject(4, fileInputStream);

        preparedStatement.execute();

        JDBCUtils.closeResource(connection, preparedStatement);
    }

    //查询数据表中的Blob类型字段
    @Test
    public void testQuery() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        InputStream binaryStream = null;
        FileOutputStream yszPhoto = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, 21);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Date birth = resultSet.getDate("birth");

                Customer customer = new Customer(id, name, email, birth);

                Blob photo = resultSet.getBlob("photo");
                binaryStream = photo.getBinaryStream();
                yszPhoto = new FileOutputStream("src/main/resources/yszPhoto.jpg");

                byte[] bytes = new byte[1024];
                int length;
                while ((length = binaryStream.read(bytes)) != -1) {
                    yszPhoto.write(bytes, 0, length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (binaryStream != null) {
                    binaryStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                binaryStream.close();
            }
            try {
                if (yszPhoto != null) {
                    yszPhoto.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                yszPhoto.close();
            }
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
    }
}
