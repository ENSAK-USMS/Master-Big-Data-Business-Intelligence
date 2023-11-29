package com.oracle.springapp.model;

import java.io.Serializable;
import java.sql.Date;

public class Orders implements Serializable {
    private Integer orderId;
    private Integer tableNumber;
    private Integer waiterId;
    private Integer cashierId;
    private Integer cookerId;
    private Date orderDate;
    private String Order_Status;

    // constructor
    public Orders() {
    }

    // getters and setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public Integer getCookerId() {
        return cookerId;
    }

    public void setCookerId(Integer cookerId) {
        this.cookerId = cookerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrder_Status() {
        return Order_Status;
    }

    public void setOrder_Status(String order_Status) {
        Order_Status = order_Status;
    }

}
