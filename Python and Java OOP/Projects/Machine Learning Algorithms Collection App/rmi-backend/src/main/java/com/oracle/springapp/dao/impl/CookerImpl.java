package com.oracle.springapp.dao.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.oracle.springapp.dao.JdbcDaoSupporter;
import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Cooker;

@Component
public class CookerImpl extends JdbcDaoSupporter implements RowDAO<Cooker> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Cooker> getAllRows() {
        String sql = "SELECT * FROM cooker";
        return (ArrayList<Cooker>) jdbcTemplate.query(sql,
                (rs, row_nbr) -> {
                    Cooker cooker = new Cooker();
                    cooker.setCookerId(rs.getInt("cooker_id"));
                    cooker.setName(rs.getString("name"));
                    cooker.setAccountId(rs.getInt("account_id"));
                    return cooker;
                });
    }

    @Override
    public Cooker insertRow(Cooker user) {
        String sql = "select max(cooker_id) from cooker";
        int[] cooker_id = new int[1];
        jdbcTemplate.query(sql,
                (resultSet, rowNumber) -> cooker_id[rowNumber] = resultSet.getInt(1));
        System.out.println("Max cooker number is: " + cooker_id[0]);
        sql = "INSERT INTO cooker VALUES(?,?,?)";
        user.setCookerId(cooker_id[0] + 1);
        jdbcTemplate.update(sql, new Object[] {
                user.getCookerId(),
                user.getName(),
                user.getAccountId()
        });
        return user;
    }

    @Override
    public Cooker findRowById(String id) {
        String sql = "SELECT * FROM cooker WHERE Cooker_ID = ?";
        Cooker cooker = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            Cooker cooker1 = new Cooker();
            cooker1.setCookerId(resultSet.getInt("cooker_id"));
            cooker1.setName(resultSet.getString("name"));
            cooker1.setAccountId(resultSet.getInt("account_id"));
            return cooker1;
        }, id);
        return cooker;
    }

    @Override
    public void updateRow(Cooker user) {
        String sql = "UPDATE cooker SET name = ? and Accoun_id = ? WHERE cooker_id = ?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getAccountId(),
                user.getCookerId());
    }

    @Override
    public void deleteRowById(String id) {
        String sql = "DELETE FROM cooker WHERE cooker_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ArrayList<Cooker> findWithCustomQuery(String query) {
        ArrayList<Cooker> cookers = new ArrayList<Cooker>();
        jdbcTemplate.query(query, (resultSet, rowNumber) -> {
            Cooker cooker = new Cooker();
            cooker.setCookerId(resultSet.getInt("cooker_id"));
            cooker.setName(resultSet.getString("name"));
            cooker.setAccountId(resultSet.getInt("account_id"));
            cookers.add(cooker);
            return cooker;
        });
        return cookers;
    }

}
