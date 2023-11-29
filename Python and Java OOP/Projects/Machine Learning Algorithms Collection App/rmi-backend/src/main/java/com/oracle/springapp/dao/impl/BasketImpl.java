package com.oracle.springapp.dao.impl;



import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.oracle.springapp.dao.JdbcDaoSupporter;
import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Basket;


@Component
public class BasketImpl extends JdbcDaoSupporter implements RowDAO<Basket> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Basket> getAllRows() {
        // log.info("Getting all rows from basket table");
        String sql = "SELECT * FROM basket";
        ArrayList<Basket> baskets = new ArrayList<Basket>();
        jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            Basket basket = new Basket();
            basket.setOrder_Id(resultSet.getInt("order_id"));
            basket.setFood_Id(resultSet.getInt("food_id"));
            basket.setQuantity(resultSet.getInt("quantity"));
            baskets.add(basket);
            return basket;
        });
        return baskets;
    }

    @Override
    public Basket insertRow(Basket row) {
        // log.info("Inserting row into basket table");
        String sql = "INSERT INTO basket VALUES(?,?,?)";
        jdbcTemplate.update(sql, new Object[] {
                row.getOrder_Id(),
                row.getFood_Id(),
                row.getQuantity()
        });
        return row;
    }

    @Override
    public Basket findRowById(String id) {
        // log.info("Finding row by id from basket table");
        String sql = "SELECT * FROM basket WHERE order_id = ?";
        Basket basket = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            Basket basket1 = new Basket();
            basket1.setOrder_Id(resultSet.getInt("order_id"));
            basket1.setFood_Id(resultSet.getInt("food_id"));
            basket1.setQuantity(resultSet.getInt("quantity"));
            return basket1;
        }, id);
        System.out.println(basket);
        return basket;
    }

    @Override
    public void updateRow(Basket row) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteRowById(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<Basket> findWithCustomQuery(String query) {
        // log.info("Finding row with custom query from basket table");
        ArrayList<Basket> baskets = new ArrayList<Basket>();
        jdbcTemplate.query(query, (resultSet, rowNumber) -> {
            Basket basket = new Basket();
            basket.setOrder_Id(resultSet.getInt("order_id"));
            basket.setFood_Id(resultSet.getInt("food_id"));
            basket.setQuantity(resultSet.getInt("quantity"));
            baskets.add(basket);
            return basket;
        });
        return baskets;
    }
}