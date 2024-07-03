package com.mana.sys.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: account message for request
 */
public class Account {
    @JsonProperty("userId")
    private long userId;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("role")
    private String role;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}