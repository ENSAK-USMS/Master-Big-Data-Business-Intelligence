package com.oracle.springapp.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Food;
import com.oracle.springapp.service.JpaService;

@Service
public class FoodService implements JpaService<Food> {

    @Autowired
    private RowDAO<Food> foodDAO;

    @Override
    public ArrayList<Food> getAllRows() {
        return foodDAO.getAllRows();
    }

    @Override
    public Food insertRow(Food food) {
        return foodDAO.insertRow(food);

    }

    @Override
    public Food findRowById(String id) {
        return foodDAO.findRowById(id);
    }

    @Override
    public void updateRow(Food food) {
        foodDAO.updateRow(food);

    }

    @Override
    public void deleteRowById(String id) {
        foodDAO.deleteRowById(id);
    }

    @Override
    public ArrayList<Food> findWithCustomQuery(String query) {
        return foodDAO.findWithCustomQuery(query);
    }

}
