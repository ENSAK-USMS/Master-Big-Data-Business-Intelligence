package com.oracle.springapp.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.service.JpaService;

import com.oracle.springapp.model.Cashier;

@Service
public class CashierService implements JpaService<Cashier> {

    @Autowired
    private RowDAO<Cashier> cashierDAO;

    @Override
    public ArrayList<Cashier> getAllRows() {
        return cashierDAO.getAllRows();
    }

    @Override
    public Cashier insertRow(Cashier cashier) {
        return cashierDAO.insertRow(cashier);

    }

    @Override
    public Cashier findRowById(String username) {
        return cashierDAO.findRowById(username);
    }

    @Override
    public void updateRow(Cashier cashier) {
        cashierDAO.updateRow(cashier);

    }

    @Override
    public void deleteRowById(String id) {
        cashierDAO.deleteRowById(id);
    }

    @Override
    public ArrayList<Cashier> findWithCustomQuery(String query) {
        return cashierDAO.findWithCustomQuery(query);
    }

}