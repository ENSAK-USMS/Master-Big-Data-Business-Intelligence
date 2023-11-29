package com.oracle.springapp.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Waiter;
import com.oracle.springapp.service.JpaService;

@Service
public class WaiterService implements JpaService<Waiter> {

    @Autowired
    private RowDAO<Waiter> waiterDAO;

    @Override
    public ArrayList<Waiter> getAllRows() {
        return waiterDAO.getAllRows();
    }

    @Override
    public Waiter insertRow(Waiter orders) {
        return waiterDAO.insertRow(orders);
    }

    @Override
    public Waiter findRowById(String id) {
        return waiterDAO.findRowById(id);
    }

    @Override
    public void updateRow(Waiter orders) {
        waiterDAO.updateRow(orders);

    }

    @Override
    public void deleteRowById(String id) {
        waiterDAO.deleteRowById(id);
    }

    @Override
    public ArrayList<Waiter> findWithCustomQuery(String query) {
        return waiterDAO.findWithCustomQuery(query);
    }

}