package xyz.mjkblog.dbutils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;
import xyz.mjkblog.bean.Customer;
import xyz.mjkblog.util.JDBCUtils;
import xyz.mjkblog.connectionPool.DruidTest;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--05--4:42 PM
 */
public class QueryRunnerTest {

    @Test
    public void testInsert() throws Exception {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = DruidTest.getConnection();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            int queryCount = runner.update(connection, sql, "ysz004", "", "");

            System.out.println(queryCount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, null);
        }
    }

    @Test
    public void testQuery1() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = DruidTest.getConnection();
            String sql = "select id,name,email,birth from customers where id=?";
            BeanHandler<Customer> customerBeanHandler = new BeanHandler<Customer>(Customer.class);
            Customer customer = runner.query(connection, sql, customerBeanHandler, 23);
            System.out.println(customer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    //BeanListHandler封装多条结果的集合
    @Test
    public void testQuery2() throws Exception{
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = DruidTest.getConnection();
            String sql = "select id,name,email,birth from customers where id=?";

            BeanListHandler<Customer> customerBeanListHandler = new BeanListHandler<Customer>(Customer.class);
            List<Customer> list = runner.query(connection, sql, customerBeanListHandler, 23);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

    @Test
    public void testQuery3() throws Exception{
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = DruidTest.getConnection();
            String sql = "select id,name,email,birth from customers where id=?";

            ResultSetHandler<Customer> handler=new ResultSetHandler<Customer>() {
                @Override
                public Customer handle(ResultSet resultSet) throws SQLException {
                    System.out.println("handler");
                    return null;
                }
            };

            Customer customer = runner.query(connection, sql, handler, 23);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,null);
        }
    }

}
