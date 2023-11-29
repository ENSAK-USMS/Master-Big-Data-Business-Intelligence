package com.oracle.springapp.model;

import java.io.Serializable;

public class Account implements Serializable {
    private Integer accountId;
    private String username;
    private String password;
    private String Role;

    // constructor
    public Account() {
    }

    // getters and setters
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "Account [AccountId=" + accountId + ", Username=" + username + ", Password=" + password + ", Role="
                + Role
                + "]";
    }

}
