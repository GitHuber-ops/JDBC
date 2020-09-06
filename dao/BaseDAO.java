package xyz.mjkblog.dao;

import xyz.mjkblog.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--05--11:29 AM
 */
public class BaseDAO {
    //通用增删改操作
    public static int update(Connection connection, String sql, Object... args) {
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
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //资源的关闭，这里请注意不要将connection关闭
            JDBCUtils.closeResource(null, preparedStatement);
        }
        return -1;
    }
}
