package com.oracle.springapp.model;

import java.io.Serializable;

public class Basket implements Serializable {
    private Integer order_Id;
    private Integer food_Id;
    private Integer quantity;

    // constructor
    public Basket() {
    }
    // getters and setters

    public Integer getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(Integer order_Id) {
        this.order_Id = order_Id;
    }

    public Integer getFood_Id() {
        return food_Id;
    }

    public void setFood_Id(Integer food_Id) {
        this.food_Id = food_Id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
