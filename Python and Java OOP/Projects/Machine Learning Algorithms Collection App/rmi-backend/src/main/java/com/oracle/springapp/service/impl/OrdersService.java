package com.oracle.springapp.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Orders;
import com.oracle.springapp.service.JpaService;

@Service
public class OrdersService implements JpaService<Orders> {

    @Autowired
    private RowDAO<Orders> ordersDAO;

    @Override
    public ArrayList<Orders> getAllRows() {
        return ordersDAO.getAllRows();
    }

    @Override
    public Orders insertRow(Orders orders) {
        return ordersDAO.insertRow(orders);

    }

    @Override
    public Orders findRowById(String id) {
        return ordersDAO.findRowById(id);
    }

    @Override
    public void updateRow(Orders orders) {
        ordersDAO.updateRow(orders);

    }

    @Override
    public void deleteRowById(String id) {
        ordersDAO.deleteRowById(id);
    }

    @Override
    public ArrayList<Orders> findWithCustomQuery(String query) {
        return ordersDAO.findWithCustomQuery(query);
    }

}