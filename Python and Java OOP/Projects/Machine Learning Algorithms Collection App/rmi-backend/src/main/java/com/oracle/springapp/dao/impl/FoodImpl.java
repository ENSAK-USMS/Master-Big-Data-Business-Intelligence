package com.oracle.springapp.dao.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.oracle.springapp.dao.RowDAO;
import com.oracle.springapp.model.Food;

@Component
public class FoodImpl implements RowDAO<Food> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Food> getAllRows() {
        String sql = "SELECT * FROM food";
        ArrayList<Food> foods = new ArrayList<Food>();
        jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            Food food = new Food();
            food.setFoodId(resultSet.getInt("food_id"));
            food.setName(resultSet.getString("name"));
            food.setPrice(resultSet.getInt("price"));
            foods.add(food);
            return food;
        });
        return foods;
    }

    @Override
    public Food insertRow(Food row) {
        String sql = "SELECT max(food_id) FROM food";
        int[] food_id = new int[1];
        jdbcTemplate.query(sql, (resultSet, rowNumber) -> food_id[rowNumber] = resultSet.getInt(1));
        System.out.println("Max food number is: " + food_id[0]);
        sql = "INSERT INTO food VALUES(?,?,?)";
        row.setFoodId(food_id[0] + 1);
        jdbcTemplate.update(sql, new Object[] {
                row.getFoodId(),
                row.getName(),
                row.getPrice()
        });
        return row;
    }

    @Override
    public Food findRowById(String id) {
        String sql = "SELECT * FROM food WHERE food_id = ?";
        Food food = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            Food food1 = new Food();
            food1.setFoodId(resultSet.getInt("food_id"));
            food1.setName(resultSet.getString("name"));
            food1.setPrice(resultSet.getInt("price"));
            return food1;
        }, id);
        return food;
    }

    @Override
    public void updateRow(Food row) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteRowById(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<Food> findWithCustomQuery(String query) {
        ArrayList<Food> foods = new ArrayList<Food>();
        jdbcTemplate.query(query, (resultSet, rowNumber) -> {
            Food food = new Food();
            food.setFoodId(resultSet.getInt("food_id"));
            food.setName(resultSet.getString("name"));
            food.setPrice(resultSet.getInt("price"));
            foods.add(food);
            return food;
        });
        return foods;
    }

}
