package com.mana.sys.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mana.sys.bean.UserEndpoint;
import com.mana.sys.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenzhiyan
 * @createDate: 2024/07/01
 * @description: unit tests for UserServiceImpl
 */
@SpringBootTest
public class UserServiceImplTests {
    @Autowired
    private UserService userService;

    @Test
    public void testAddAccess() throws Exception {
        List<UserEndpoint> userEndpoints = new ArrayList<>();
        Assertions.assertThrows(RuntimeException.class,() -> {
            userService.addAccess(userEndpoints);
        });
        String userEndpointJson = "{\"userId\":123456,\"endpoint\" :[\"resource C\",\"resource D\"]}";
        ObjectMapper mapperAccount = new ObjectMapper();
        UserEndpoint userEndpoint = mapperAccount.readValue(userEndpointJson, UserEndpoint.class);
        userEndpoints.add(userEndpoint);
        Assertions.assertDoesNotThrow(() -> {
            userService.addAccess(userEndpoints);
        });
     }

    @Test
    public void testCheckAccess() throws Exception {
        long userId = 123456l;
        boolean result = userService.checkAccess(userId, Strings.EMPTY);
        Assertions.assertFalse(result);
        Assertions.assertFalse(userService.checkAccess(userId, File.separator));
    }
}