package com.mana.sys.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: resource and user access
 */
public class UserEndpoint {
    @JsonProperty("userId")
    private long userId;

    @JsonProperty("endpoint")
    private String[] endpoint;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String[] getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String[] endpoint) {
        this.endpoint = endpoint;
    }
}