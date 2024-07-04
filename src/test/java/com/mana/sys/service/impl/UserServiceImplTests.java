package com.mana.sys.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mana.sys.bean.UserEndpoint;
import com.mana.sys.constants.SysConstants;
import com.mana.sys.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public void testAddAccess() {
        List<UserEndpoint> userEndpoints = new ArrayList<>();
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.addAccess(userEndpoints);
        });
        long userId = 123456l;
        String userEndpointJson = "{\"userId\":" + userId + ",\"endpoint\" :[\"resource C\",\"resource D\"]}";
        ObjectMapper mapperAccount = new ObjectMapper();
        Assertions.assertDoesNotThrow(() -> {
            UserEndpoint userEndpoint = mapperAccount.readValue(userEndpointJson, UserEndpoint.class);
            userEndpoints.add(userEndpoint);
        });
        Assertions.assertDoesNotThrow(() -> {
            userService.addAccess(userEndpoints);
        });
        String pathStr = SysConstants.RESOURCE_DIR + File.separator
                + userId + SysConstants.FILE_SUFFIX;
        Assertions.assertTrue(Files.exists(Paths.get(pathStr)));
    }

    @Test
    public void testCheckAccess() {
        long userId = 123456l;
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertFalse(userService.checkAccess(userId, Strings.EMPTY));
        });
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertFalse(userService.checkAccess(userId, File.separator));
        });
    }
}