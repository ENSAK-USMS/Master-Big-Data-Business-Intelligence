package com.oracle.springapp.model;

import java.io.Serializable;

public class Waiter implements Serializable {
    private Integer waiterId;
    private String name;
    private Integer accountId;

    // constructor
    public Waiter() {
    }

    // getters and setters
    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
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
