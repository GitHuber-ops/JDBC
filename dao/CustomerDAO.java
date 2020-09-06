package xyz.mjkblog.dao;

import xyz.mjkblog.bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--05--11:32 AM
 * @Description: 用于规范对于Customers表的操作
 */
public interface CustomerDAO {
    //将customer对象添加到数据库对象
    void insert(Connection connection, Customer customer);

    //根据指定的id删除表中的一条记录
    void deleteById(Connection connection, int id);

    //根据指定的id修改表中的一条记录
    void updateById(Connection connection, Customer customer);

    //根据指定的id查询表中的一条记录
    void getCustomerById(Connection connection, int id);

    //查询表中所以记录构成的集合
    List<Customer> getAll(Connection connection);

    //返回数据表中数据的数目
    Long getCount(Connection connection);

    //返回表中最大的生日（年龄最小）
    Date getMaxBirth(Connection connection);
}
