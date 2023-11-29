package com.oracle.springapp.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Cooker;

import com.oracle.springapp.service.JpaService;

@Service
public class CookerService implements JpaService<Cooker> {

    @Autowired
    private RowDAO<Cooker> cookerDAO;

    @Override
    public ArrayList<Cooker> getAllRows() {
        return cookerDAO.getAllRows();
    }

    @Override
    public Cooker insertRow(Cooker cooker) {
        return cookerDAO.insertRow(cooker);

    }

    @Override
    public Cooker findRowById(String username) {
        return cookerDAO.findRowById(username);
    }

    @Override
    public void updateRow(Cooker cooker) {
        cookerDAO.updateRow(cooker);

    }

    @Override
    public void deleteRowById(String id) {
        cookerDAO.deleteRowById(id);
    }

    @Override
    public ArrayList<Cooker> findWithCustomQuery(String query) {
        return cookerDAO.findWithCustomQuery(query);
    }
}