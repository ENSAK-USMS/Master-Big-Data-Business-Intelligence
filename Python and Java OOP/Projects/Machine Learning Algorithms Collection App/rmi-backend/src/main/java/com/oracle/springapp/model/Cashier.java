package com.oracle.springapp.model;

import java.io.Serializable;

public class Cashier implements Serializable {
    private Integer cashierId;
    private String name;
    private Integer accountId;
    // constructor

    public Cashier() {
    }

    // getters and setters
    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
