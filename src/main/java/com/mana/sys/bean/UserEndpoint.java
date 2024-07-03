package com.mana.sys.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: resource and user access
 */
@Getter
@Setter
public class UserEndpoint {
    @JsonProperty("userId")
    private long userId;

    @JsonProperty("endpoint")
    private String[] endpoint;
}