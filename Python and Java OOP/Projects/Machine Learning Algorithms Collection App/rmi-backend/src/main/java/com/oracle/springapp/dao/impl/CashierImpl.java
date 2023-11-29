package com.oracle.springapp.dao.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.oracle.springapp.dao.JdbcDaoSupporter;
import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Cashier;

@Component
public class CashierImpl extends JdbcDaoSupporter implements RowDAO<Cashier> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Cashier> getAllRows() {
        String sql = "SELECT * FROM cashier";
        ArrayList<Cashier> cashiers = new ArrayList<Cashier>();
        jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            Cashier cashier = new Cashier();
            cashier.setCashierId(resultSet.getInt("cashier_id"));
            cashier.setName(resultSet.getString("name"));
            cashier.setAccountId(resultSet.getInt("account_id"));
            cashiers.add(cashier);
            return cashier;
        });
        return cashiers;
    }

    @Override
    public Cashier insertRow(Cashier user) {
        // TODO Auto-generated method stub
        String sql = "select max(cashier_id) from cashier";
        int[] cashier_id = new int[1];
        jdbcTemplate.query(sql,
                (resultSet, rowNumber) -> cashier_id[rowNumber] = resultSet.getInt(1));
        System.out.println("Max cashier number is: " + cashier_id[0]);
        user.setCashierId(cashier_id[0] + 1);
        sql = "INSERT INTO cashier VALUES(?,?,?)";
        jdbcTemplate.update(sql, new Object[] {
                user.getCashierId(),
                user.getName(),
                user.getAccountId()
        });
        return user;
    }

    @Override
    public Cashier findRowById(String id) {
        String sql = "SELECT * FROM cashier WHERE cashier_id = ?";
        Cashier cashier = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            Cashier cashier1 = new Cashier();
            cashier1.setCashierId(resultSet.getInt("cashier_id"));
            cashier1.setName(resultSet.getString("name"));
            cashier1.setAccountId(resultSet.getInt("account_id"));
            return cashier1;
        }, id);
        return cashier;
    }

    @Override
    public void updateRow(Cashier user) {
        String sql = "UPDATE cashier SET name = ? and Accoun_id = ? WHERE cashier_id = ?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getAccountId(),
                user.getCashierId());
    }

    @Override
    public void deleteRowById(String id) {
        String sql = "DELETE FROM cashier WHERE cashier_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ArrayList<Cashier> findWithCustomQuery(String query) {
        ArrayList<Cashier> cashiers = new ArrayList<Cashier>();
        jdbcTemplate.query(query, (resultSet, rowNumber) -> {
            Cashier cashier = new Cashier();
            cashier.setCashierId(resultSet.getInt("cashier_id"));
            cashier.setName(resultSet.getString("name"));
            cashier.setAccountId(resultSet.getInt("account_id"));
            cashiers.add(cashier);
            return cashier;
        });
        return cashiers;
    }

}
