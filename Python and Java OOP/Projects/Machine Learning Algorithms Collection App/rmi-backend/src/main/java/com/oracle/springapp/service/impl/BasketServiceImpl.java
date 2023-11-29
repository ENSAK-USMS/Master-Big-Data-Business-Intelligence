package com.oracle.springapp.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Basket;
import com.oracle.springapp.service.JpaService;

@Service
public class BasketServiceImpl implements JpaService<Basket> {

    @Autowired
    private RowDAO<Basket> basketDAO;

    @Override
    public ArrayList<Basket> getAllRows() {
        return basketDAO.getAllRows();
    }

    @Override
    public Basket insertRow(Basket basket) {
        return basketDAO.insertRow(basket);
    }

    @Override
    public Basket findRowById(String id) {
        return basketDAO.findRowById(id);
    }

    @Override
    public void updateRow(Basket basket) {
        basketDAO.updateRow(basket);

    }

    @Override
    public void deleteRowById(String id) {
        basketDAO.deleteRowById(id);
    }

    @Override
    public ArrayList<Basket> findWithCustomQuery(String query) {
        return basketDAO.findWithCustomQuery(query);
    }

}