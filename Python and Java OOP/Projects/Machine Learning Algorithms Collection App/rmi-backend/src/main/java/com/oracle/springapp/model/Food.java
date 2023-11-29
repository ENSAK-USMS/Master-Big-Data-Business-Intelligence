package com.oracle.springapp.model;

import java.io.Serializable;

public class Food implements Serializable {
    private Integer foodId;
    private String name;
    private Integer price;

    // constructor
    public Food() {
    }

    // getters and setters
    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
