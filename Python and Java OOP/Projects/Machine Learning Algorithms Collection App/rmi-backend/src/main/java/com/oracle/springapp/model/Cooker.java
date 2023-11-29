package com.oracle.springapp.model;

import java.io.Serializable;

public class Cooker implements Serializable {
    private Integer cookerId;
    private String name;
    private Integer accountId;

    // constructor
    public Cooker() {
    }

    // getters and setters
    public Integer getCookerId() {
        return cookerId;
    }

    public void setCookerId(Integer cookerId) {
        this.cookerId = cookerId;
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
