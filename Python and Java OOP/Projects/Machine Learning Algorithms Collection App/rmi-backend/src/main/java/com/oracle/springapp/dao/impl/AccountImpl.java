package com.oracle.springapp.dao.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.oracle.springapp.model.Account;


import com.oracle.springapp.dao.JdbcDaoSupporter;
import com.oracle.springapp.dao.RowDAO;


@Component
public class AccountImpl extends JdbcDaoSupporter implements RowDAO<Account> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Account> getAllRows() {
        // log.info("Getting all rows from account table");
        String sql = "SELECT * FROM account";
        ArrayList<Account> accounts = new ArrayList<Account>();
        jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            Account account = new Account();
            account.setAccountId(resultSet.getInt("account_id"));
            account.setUsername(resultSet.getString("username"));
            account.setPassword(resultSet.getString("password"));
            account.setRole(resultSet.getString("role"));
            accounts.add(account);
            return account;
        });
        return accounts;
    }

    @Override
    public Account insertRow(Account row) {
        // log.info("Inserting row into account table");
        String sql = "select max(account_id) from account";
        int[] account_id = new int[1];
        jdbcTemplate.query(sql, (resultSet, rowNumber) -> account_id[rowNumber] = resultSet.getInt(1));
        System.out.println("Max account number is: " + account_id[0]);

        sql = "INSERT INTO account VALUES(?,?,?,?)";
        row.setAccountId(account_id[0] + 1);
        jdbcTemplate.update(sql, new Object[] {
                row.getAccountId(),
                row.getUsername(),
                row.getPassword(),
                row.getRole()
        });
        return row;
    }

    @Override
    public Account findRowById(String id) {
        // log.info("Finding row by id from account table");
        System.out.println("Account found: " + id);
        try {

            String sql = "SELECT * FROM account WHERE username = ?";
            Account account = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
                Account account1 = new Account();
                account1.setAccountId(resultSet.getInt("account_id"));
                account1.setUsername(resultSet.getString("username"));
                account1.setPassword(resultSet.getString("password"));
                account1.setRole(resultSet.getString("role"));
                return account1;
            }, id);
            return account;
        } catch (Exception e) {
            System.out.println("got you " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateRow(Account user) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteRowById(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<Account> findWithCustomQuery(String query) {
        // log.info("Finding row by custom query from account table");
        ArrayList<Account> accounts = new ArrayList<Account>();
        jdbcTemplate.query(query, (resultSet, rowNumber) -> {
            Account account = new Account();
            account.setAccountId(resultSet.getInt("account_id"));
            account.setUsername(resultSet.getString("username"));
            account.setPassword(resultSet.getString("password"));
            accounts.add(account);
            return account;
        });
        return accounts;
    }

}
