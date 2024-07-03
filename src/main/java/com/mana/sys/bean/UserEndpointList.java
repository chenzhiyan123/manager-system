package com.mana.sys.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: UserEndpoint list
 */
@Getter
@Setter
public class UserEndpointList {
    @JsonProperty("endpoints")
    private List<UserEndpoint> endpoints;
}