package com.oracle.springapp.dao.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.oracle.springapp.model.Waiter;
import com.oracle.springapp.dao.JdbcDaoSupporter;
import com.oracle.springapp.dao.RowDAO;

@Component
public class WaiterImpl extends JdbcDaoSupporter implements RowDAO<Waiter> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Waiter> getAllRows() {
        String sql = "SELECT * FROM waiter";
        return (ArrayList<Waiter>) jdbcTemplate.query(sql,
                (rs, row_nbr) -> {
                    Waiter waiter = new Waiter();
                    waiter.setWaiterId(rs.getInt("waiter_id"));
                    waiter.setName(rs.getString("name"));
                    waiter.setAccountId(rs.getInt("account_id"));
                    return waiter;
                });
    }

    @Override
    public Waiter insertRow(Waiter user) {
        String sql = "select max(waiter_id) from waiter";
        int[] waiter_id = new int[1];
        jdbcTemplate.query(sql,
                (resultSet, rowNumber) -> waiter_id[rowNumber] = resultSet.getInt(1));
        System.out.println("Max waiter number is: " + waiter_id[0]);
        sql = "INSERT INTO waiter VALUES(?,?,?)";
        user.setWaiterId(waiter_id[0] + 1);
        jdbcTemplate.update(sql, new Object[] {
                user.getWaiterId(),
                user.getName(),
                user.getAccountId()
        });
        return user;
    }

    @Override
    public Waiter findRowById(String id) {
        String sql = "SELECT * FROM waiter WHERE Waiter_ID = ?";
        Waiter waiter = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            Waiter waiter1 = new Waiter();
            waiter1.setWaiterId(resultSet.getInt("waiter_id"));
            waiter1.setName(resultSet.getString("name"));
            waiter1.setAccountId(resultSet.getInt("account_id"));
            return waiter1;
        }, id);
        return waiter;
    }

    @Override
    public void updateRow(Waiter user) {
        String sql = "UPDATE waiter SET name = ? and Accoun_id = ? WHERE waiter_id = ?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getAccountId(),
                user.getWaiterId());
    }

    @Override
    public void deleteRowById(String id) {
        String sql = "DELETE FROM waiter WHERE waiter_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ArrayList<Waiter> findWithCustomQuery(String query) {
        ArrayList<Waiter> waiters = new ArrayList<Waiter>();
        jdbcTemplate.query(query, (resultSet, rowNumber) -> {
            Waiter waiter = new Waiter();
            waiter.setWaiterId(resultSet.getInt("waiter_id"));
            waiter.setName(resultSet.getString("name"));
            waiter.setAccountId(resultSet.getInt("account_id"));
            waiters.add(waiter);
            return waiter;
        });
        return waiters;
    }

}
