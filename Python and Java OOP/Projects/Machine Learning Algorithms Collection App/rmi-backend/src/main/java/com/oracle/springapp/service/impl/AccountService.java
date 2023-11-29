package com.oracle.springapp.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.springapp.model.Account;
import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.service.JpaService;

@Service
public class AccountService implements JpaService<Account> {

    @Autowired
    private RowDAO<Account> accountDAO;

    @Override
    public ArrayList<Account> getAllRows() {
        return accountDAO.getAllRows();
    }

    @Override
    public Account insertRow(Account account) {
        return accountDAO.insertRow(account);
    }

    @Override
    public Account findRowById(String username) {
        return accountDAO.findRowById(username);
    }

    @Override
    public void updateRow(Account account) {
        accountDAO.updateRow(account);

    }

    @Override
    public void deleteRowById(String id) {
        accountDAO.deleteRowById(id);
    }

    @Override
    public ArrayList<Account> findWithCustomQuery(String query) {
        return accountDAO.findWithCustomQuery(query);
    }

}