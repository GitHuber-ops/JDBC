package xyz.mjkblog.blob;

import org.junit.Test;
import xyz.mjkblog.util.JDBCUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--04--9:01 PM
 * @Description:使用preparedStatement实现批量数据的操作
 */
public class InsertTest {
    @Test
    public void testInsert1() throws Exception {

        //使用PreparedStatement操作
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < 20000; i++) {
                preparedStatement.setObject(1, "name_" + i);
                preparedStatement.execute();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }
    }

    //addBatch(),executeBatch(),cleatBatch()
    @Test
    public void testInsert2() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            //关闭自动提交
            connection.setAutoCommit(false);

            String sql = "insert into customers(name)values(?)";
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < 20000; i++) {
                preparedStatement.setObject(1, "name_" + i);
                //积累SQL
                preparedStatement.addBatch();

                if (i % 500 == 0) {
                    //执行Batch
                    preparedStatement.executeBatch();
                    //清空Batch
                    preparedStatement.clearBatch();
                }
            }
            //一次性提交所有数据
            connection.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }
    }
}

