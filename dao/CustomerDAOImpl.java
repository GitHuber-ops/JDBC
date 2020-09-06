package xyz.mjkblog.dao;

import xyz.mjkblog.bean.Customer;
import xyz.mjkblog.dao.BaseDAO;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @Project: JDBC
 * @Author: Unknown
 * @Create: 2020--09--05--11:40 AM
 */
public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void insert(Connection connection, Customer customer) {
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        BaseDAO.update(connection, sql, customer.getName(), customer.getEmail(), customer.getBirth());
    }

    @Override
    public void deleteById(Connection connection, int id) {

    }

    @Override
    public void updateById(Connection connection, Customer customer) {

    }

    @Override
    public void getCustomerById(Connection connection, int id) {

    }

    @Override
    public List<Customer> getAll(Connection connection) {
        return null;
    }

    @Override
    public Long getCount(Connection connection) {
        return null;
    }

    @Override
    public Date getMaxBirth(Connection connection) {
        return null;
    }
}
