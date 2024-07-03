package com.mana.sys.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: chenzhiyan
 * @createDate: 2024/06/30
 * @description: account message for request
 */
@Getter
@Setter
public class Account {
    @JsonProperty("userId")
    private long userId;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("role")
    private String role;
}