package com.mana.sys.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: UserEndpoint list
 */
public class UserEndpointList {
    @JsonProperty("endpoints")
    private List<UserEndpoint> endpoints;

    public List<UserEndpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<UserEndpoint> endpoints) {
        this.endpoints = endpoints;
    }
}