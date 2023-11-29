package com.oracle.springapp.dao.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.oracle.springapp.dao.JdbcDaoSupporter;
import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Orders;

@Component
public class OrdersImpl extends JdbcDaoSupporter implements RowDAO<Orders> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Orders> getAllRows() {
        String sql = "SELECT * FROM orders";
        ArrayList<Orders> orders = new ArrayList<Orders>();
        jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            Orders order = new Orders();
            order.setOrderId(resultSet.getInt("order_id"));
            order.setTableNumber(resultSet.getInt("table_number"));
            order.setWaiterId(resultSet.getInt("waiter_id"));
            order.setCashierId(resultSet.getInt("cashier_id"));
            order.setCookerId(resultSet.getInt("cooker_id"));
            order.setOrderDate(resultSet.getDate("order_date"));
            order.setOrder_Status(resultSet.getString("order_status"));
            orders.add(order);
            return order;
        });
        return orders;
    }

    @Override
    public Orders insertRow(Orders row) {
        String sql = "select max(order_id) from orders";
        int[] order_id = new int[1];
        jdbcTemplate.query(sql, (resultSet, rowNumber) -> order_id[rowNumber] = resultSet.getInt(1));
        try {
            sql = "INSERT INTO orders (Order_ID, Table_Number, Waiter_ID,Order_Date)" +
                    " VALUES(?,?,?, to_date(?, 'yyyy-mm-dd'))";
            // get current date in injectable format
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(date);
            row.setOrderId(order_id[0] + 1);
            jdbcTemplate.update(sql, new Object[] {
                    row.getOrderId(),
                    row.getTableNumber(),
                    row.getWaiterId(),
                    currentDate
            });
            return row;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Orders findRowById(String id) {
        String sql = "SELECT * FROM orders WHERE Order_ID = ?";
        Orders order = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            Orders order1 = new Orders();
            order1.setOrderId(resultSet.getInt("order_id"));
            order1.setTableNumber(resultSet.getInt("table_number"));
            order1.setWaiterId(resultSet.getInt("waiter_id"));
            order1.setCashierId(resultSet.getInt("cashier_id"));
            order1.setCookerId(resultSet.getInt("cooker_id"));
            order1.setOrderDate(resultSet.getDate("order_date"));
            order1.setOrder_Status(resultSet.getString("order_status"));
            return order1;
        }, id);
        return order;
    }

    @Override
    public void updateRow(Orders row) {
        String sql = "UPDATE orders "+
        "SET table_number = ?, waiter_id = ?, cashier_id = ?, cooker_id = ?, order_date = to_date(?, 'yyyy-mm-dd') , Order_Status = ?"+
        " WHERE order_id = ?";
        jdbcTemplate.update(sql, new Object[] {
                row.getTableNumber(),
                row.getWaiterId(),
                row.getCashierId(),
                row.getCookerId(),
                row.getOrderDate(),
                row.getOrder_Status(),
                row.getOrderId()
        });
    }

    @Override
    public void deleteRowById(String id) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ArrayList<Orders> findWithCustomQuery(String query) {
        ArrayList<Orders> orders = new ArrayList<Orders>();
        jdbcTemplate.query(query, (resultSet, rowNumber) -> {
            Orders order = new Orders();
            order.setOrderId(resultSet.getInt("order_id"));
            order.setTableNumber(resultSet.getInt("table_number"));
            order.setWaiterId(resultSet.getInt("waiter_id"));
            order.setCashierId(resultSet.getInt("cashier_id"));
            order.setCookerId(resultSet.getInt("cooker_id"));
            order.setOrderDate(resultSet.getDate("order_date"));
            order.setOrder_Status(resultSet.getString("order_status"));
            orders.add(order);
            return order;
        });
        return orders;
    }
}
